package br.gov.agu.nutec.msescala.service;

import br.gov.agu.nutec.msescala.dto.message.PautaMessage;
import br.gov.agu.nutec.msescala.entity.*;
import br.gov.agu.nutec.msescala.enums.StatusCadastro;
import br.gov.agu.nutec.msescala.repository.AudienciaRepository;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static br.gov.agu.nutec.msescala.enums.StatusCadastro.ERRO;
import static br.gov.agu.nutec.msescala.enums.StatusCadastro.SUCESSO;

@Service
@RequiredArgsConstructor
public class CadastrarTarefaService {

    private final AudienciaRepository audienciaRepository;
    private final WebClient webClient;
    


    public void cadastrarTarefaSapiens(AudienciaEntity audiencia, PautaEntity pauta, EntidadeSapiens entidadeSapiens, PautaMessage pautaMessage) {
        Integer processoId = buscarProcessoIdPorAudiencia(audiencia.getNumeroProcesso(), pautaMessage.token());
        var statusCodeCaastroTarefa = cadastrarAudienciaParaUsuarioSapiens(processoId,pautaMessage.setorOrigemId(),pautaMessage.especieTarefaId(), entidadeSapiens,pauta, pautaMessage.token());
        StatusCadastro  statusCadastro = (processoId == null || !statusCodeCaastroTarefa.is2xxSuccessful()) ? ERRO : SUCESSO;
        atualizarStatus(audiencia, entidadeSapiens, statusCadastro);
        audienciaRepository.save(audiencia);
    }



    private Integer buscarProcessoIdPorAudiencia(String numeroProcesso, String token) {
        String numeroProcessoDesformatado = numeroProcesso.replaceAll("[\\.\\-]", "");

        // Construct the where clause without encoding it prematurely
        String where = String.format(
                "{\"vinculacoesProcessosJudiciaisProcessos.processoJudicial.numero\":\"like:%s\"}",
                numeroProcessoDesformatado
        );

        try {
            String uri = "/v1/administrativo/processo";

            JsonNode response = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path(uri)
                            .queryParam("where", "{where}") // Placeholder for the where clause
                            .queryParam("limit", 1)
                            .queryParam("offset", 0)
                            .build(where)) // Pass the where clause here to avoid double encoding
                    .header("Authorization", "Bearer " + token)
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .block(Duration.ofSeconds(30));

            return extrairProcessoIdFromJsonNode(response);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private HttpStatusCode cadastrarAudienciaParaUsuarioSapiens(
            Integer processoId,
            Integer setorOrigemId,
            Integer especieTarefaId,
            EntidadeSapiens entidadeSapiens,
            PautaEntity pauta,
            String token
    ) {
        Map<String, Object> body = new HashMap<>();
        body.put("postIt", null);
        body.put("urgente", null);
        body.put("observacao", String.format("%s - %s", pauta.getData().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")), pauta.getOrgaoJulgador().getNome()));        body.put("localEvento", null);
        body.put("dataHoraInicioPrazo", LocalDateTime.now());
        body.put("dataHoraFinalPrazo", pauta.getData().atTime(23, 59, 59).toString());
        body.put("dataHoraLeitura", null);
        body.put("dataHoraDistribuicao", null);
        body.put("processo", processoId);
        body.put("especieTarefa", especieTarefaId); // Espécie Tarefa
        body.put("usuarioResponsavel", entidadeSapiens.getSapiensId()); //Quem vai receber a tarefa
        body.put("setorOrigem", setorOrigemId);
        body.put("setorResponsavel", entidadeSapiens.getSetorId());
        body.put("distribuicaoAutomatica", false);
        body.put("folder", null);
        body.put("isRelevante", null);
        body.put("locked", null);
        body.put("diasUteis", null);
        body.put("blocoProcessos", null);
        body.put("processos", null);
        body.put("blocoResponsaveis", null);
        body.put("grupoContato", null);
        body.put("usuarios", null);
        body.put("setores", null);

        return webClient.post()
                .uri("/v1/administrativo/tarefa")
                .header("Authorization", "Bearer " + token)
                .bodyValue(body)
                .retrieve()
                .toBodilessEntity()
                .map(ResponseEntity::getStatusCode)
                .block();
    }





    private static Integer extrairProcessoIdFromJsonNode(JsonNode processoJson) {

        Integer processoId = null;
        JsonNode entitiesNode = processoJson.get("entities");
        if (!entitiesNode.isEmpty()) {
            JsonNode firstEntity = entitiesNode.get(0);
            processoId = firstEntity.get("id").asInt();
        }
        return processoId;
    }

    private void atualizarStatus(AudienciaEntity audienciaEntity, EntidadeSapiens entidade, StatusCadastro statusCadastro) {

        if (entidade instanceof PautistaEntity){
            audienciaEntity.setStatusCadastroPautista(statusCadastro);
        }

        if (entidade instanceof AvaliadorEntity){
            audienciaEntity.setStatusCadastroAvaliador(statusCadastro);
        }
    }
}