package br.gov.agu.nutec.msescala.lister;

import br.gov.agu.nutec.msescala.entity.PautaEntity;
import br.gov.agu.nutec.msescala.service.EscalaService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EscalaListener {

    private final EscalaService escalaService;

    @RabbitListener(queues = "${rabbitmq.queue.escala-pendente-avaliador}")
    public void escalarAvaliadores(PautaEntity pauta) {
        escalaService.escalarAvaliadores(pauta);
    }
    
    @RabbitListener(queues = "${rabbitmq.queue.escala-pendente-pautista}")
    public void escalarPautistas(PautaEntity pauta) {
        escalaService.escalarPautistas(pauta);
    }
}
