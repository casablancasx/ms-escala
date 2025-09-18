package br.gov.agu.nutec.msescala.producer;

import br.gov.agu.nutec.msescala.dto.EscalaRequestDTO;
import br.gov.agu.nutec.msescala.entity.PautaEntity;
import br.gov.agu.nutec.msescala.repository.PautaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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


    public void iniciarEscalaAvaliadores(EscalaRequestDTO request) {

        List<PautaEntity> pautas = pautaRepository.buscarPautasSemAvaliadoresEscalados(
                request.dataIncio(),
                request.dataFim(),
                request.uf());

        pautas.parallelStream().forEach(pauta -> {
            rabbitTemplate.convertAndSend(exchange, bindingKeyAvaliador, pauta);
        });
    }

    public void iniciarEscalaPautistas(EscalaRequestDTO escalaRequestDTO) {

        List<PautaEntity> pautas = pautaRepository.buscarPautasSemPautistaEscalados(
                escalaRequestDTO.dataIncio(),
                escalaRequestDTO.dataFim(),
                escalaRequestDTO.uf());

        pautas.parallelStream().forEach(pauta -> {
            rabbitTemplate.convertAndSend(exchange, bindingKeyPautista, pauta);
        });
    }
}
