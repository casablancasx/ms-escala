package br.gov.agu.nutec.msescala.service;

import br.gov.agu.nutec.msescala.entity.AudienciaEntity;
import br.gov.agu.nutec.msescala.entity.AvaliadorEntity;
import br.gov.agu.nutec.msescala.entity.EntidadeSapiens;
import br.gov.agu.nutec.msescala.entity.PautistaEntity;
import br.gov.agu.nutec.msescala.enums.StatusCadastro;
import br.gov.agu.nutec.msescala.repository.AudienciaRepository;
import static br.gov.agu.nutec.msescala.enums.StatusCadastro.ERRO;
import static br.gov.agu.nutec.msescala.enums.StatusCadastro.SUCESSO;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CadastrarTarefaService {

    private final AudienciaRepository audienciaRepository;
    private final WebClient webClient;
    


    public void cadastrarTarefaSapiens(AudienciaEntity audiencia, EntidadeSapiens entidadeSapiens, String token) {
        Integer processoId = buscarProcessoIdPorAudiencia(audiencia.getNumeroProcesso(), token);
        var statusCodeCaastroTarefa = cadastrarAudienciaParaUsuarioSapiens(processoId, entidadeSapiens, token);
        StatusCadastro  statusCadastro = (processoId == null || !statusCodeCaastroTarefa.is2xxSuccessful()) ? ERRO : SUCESSO;
        atualizarStatus(audiencia, entidadeSapiens, statusCadastro);
        audienciaRepository.save(audiencia);
    }



    private Integer buscarProcessoIdPorAudiencia(String numeroProcesso, String token) {
        String numeroProcessoDesformatado = numeroProcesso.replaceAll("[\\.\\-]", "");

        String where = String.format(
                "{\"vinculacoesProcessosJudiciaisProcessos.processoJudicial.numero\":\"like:%s\"}",
                numeroProcessoDesformatado
        );

        String whereEncoded = UriUtils.encode(where, StandardCharsets.UTF_8);
        try {
            String uri = "/v1/administrativo/processo?where=" + whereEncoded + "&limit=1&offset=0";
            
            JsonNode response = webClient.get()
                    .uri(uri)
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


    private HttpStatusCode cadastrarAudienciaParaUsuarioSapiens(Integer processoId, EntidadeSapiens entidadeSapiens, String token) {
        var body = Map.ofEntries(
                Map.entry("postIt", null),
                Map.entry("urgente", null),
                Map.entry("observacao", "TESTE PAUTA DANILO"),
                Map.entry("localEvento", null),
                Map.entry("dataHoraInicioPrazo", "2025-09-18T14:22:29"),
                Map.entry("dataHoraFinalPrazo", "2025-09-23T20:00:00"),
                Map.entry("dataHoraLeitura", null),
                Map.entry("dataHoraDistribuicao", null),
                Map.entry("processo", processoId),
                Map.entry("especieTarefa", 105),
                Map.entry("usuarioResponsavel", entidadeSapiens.getSapiensId()),
                Map.entry("setorOrigem", 41430),
                Map.entry("setorResponsavel", 41430),
                Map.entry("distribuicaoAutomatica", false),
                Map.entry("folder", null),
                Map.entry("isRelevante", null),
                Map.entry("locked", null),
                Map.entry("diasUteis", null),
                Map.entry("prazoDias", 5),
                Map.entry("blocoProcessos", null),
                Map.entry("processos", null),
                Map.entry("blocoResponsaveis", null),
                Map.entry("grupoContato", null),
                Map.entry("usuarios", null),
                Map.entry("setores", null)
        );

        return webClient.post()
                .uri("/v1/administrativo/tarefa")
                .header("Authorization", "Bearer " + token)
                .bodyValue(body)
                .retrieve()
                .toBodilessEntity()
                .map(ResponseEntity::getStatusCode).block();
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