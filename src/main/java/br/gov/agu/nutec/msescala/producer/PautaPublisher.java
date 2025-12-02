package br.gov.agu.nutec.msescala.producer;

import br.gov.agu.nutec.msescala.dto.message.PautaMessage;
import br.gov.agu.nutec.msescala.dto.request.EscalaRequestDTO;
import br.gov.agu.nutec.msescala.entity.PautaEntity;
import br.gov.agu.nutec.msescala.repository.PautaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PautaPublisher {

    private final PautaRepository pautaRepository;
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.pauta-escalar-pendente}")
    private String exchange;

    @Value("${rabbitmq.bindingkey.avaliador}")
    private String bindingKeyAvaliador;

    @Value("${rabbitmq.bindingkey.pautista}")
    private String bindingKeyPautista;


    public void iniciarEscalaAvaliadores(EscalaRequestDTO request, String token) {

        List<PautaEntity> pautas = pautaRepository.buscarPautasSemAvaliadoresEscalados(
                request.dataInicio(),
                request.dataFim(),
                request.ufs().isEmpty() ? null : request.ufs(),
                request.orgaoJulgadorIds().isEmpty() ? null : request.orgaoJulgadorIds(),
                request.tipoContestacao().isEmpty() ? null : request.tipoContestacao()
        );

        pautas.parallelStream().forEach(pauta -> {
            rabbitTemplate.convertAndSend(
                    exchange,
                    bindingKeyAvaliador,
                    new PautaMessage("Pauta para escala de avaliador", pauta.getPautaId(), request.setorOrigemId(), request.especieTarefaId(), request.avaliadorIds(), request.pautistaIds(), token));
        });

    }

}
