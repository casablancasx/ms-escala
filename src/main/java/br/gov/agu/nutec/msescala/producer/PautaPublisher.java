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

        List<PautaEntity> pautas = new ArrayList<>();


        if (!request.ufIds().isEmpty() && request.orgaoJulgadorIds().isEmpty()) {

            pautas = pautaRepository.buscarPautasSemAvaliadoresEscaladosPorUf(
                    request.dataInicio(),
                    request.dataFim(),
                    request.ufIds());

        }

        if (request.orgaoJulgadorIds().isEmpty() && request.ufIds().isEmpty()) {
            pautas = pautaRepository.buscarPautasSemAvaliadoresEscaladosPorPeriodo(
                    request.dataInicio(),
                    request.dataFim()
            );
        }

        if (!request.orgaoJulgadorIds().isEmpty()) {
            pautas = pautaRepository.buscarPautasSemAvaliadoresEscaladosPorOrgaoJulgador(
                    request.dataInicio(),
                    request.dataFim(),
                    request.orgaoJulgadorIds()
            );

            pautas.parallelStream().forEach(pauta -> {
                rabbitTemplate.convertAndSend(
                        exchange,
                        bindingKeyAvaliador,
                        new PautaMessage("Pauta para escala de avaliador", pauta.getPautaId(), request.setorOrigemId(), request.especieTarefaId(), request.avaliadorIds(), request.pautistaIds(), token));
            });
        }

    }

    public void iniciarEscalaPautistas(EscalaRequestDTO request, String token) {

        List<PautaEntity> pautas = new ArrayList<>();

        if (!request.ufIds().isEmpty() && request.orgaoJulgadorIds().isEmpty()) {
            pautas = pautaRepository.buscarPautasSemPautistasEscaladosPorUf(
                    request.dataInicio(),
                    request.dataFim(),
                    request.ufIds());
        }

        if (request.orgaoJulgadorIds().isEmpty() && request.ufIds().isEmpty()) {
            pautas = pautaRepository.buscarPautasSemPautistasEscaladosPorPeriodo(
                    request.dataInicio(),
                    request.dataFim()
            );
        }

        if (!request.orgaoJulgadorIds().isEmpty()) {
            pautas = pautaRepository.buscarPautasSemPautistasEscaladosPorOrgaoJulgador(
                    request.dataInicio(),
                    request.dataFim(),
                    request.orgaoJulgadorIds()
            );
        }

        pautas.parallelStream().forEach(pauta -> {
            rabbitTemplate.convertAndSend(exchange, bindingKeyPautista, new PautaMessage("Pauta para escala de pautista", pauta.getPautaId(), request.setorOrigemId(), request.especieTarefaId(),request.avaliadorIds(),request.pautistaIds(), token));
        });
    }
}
