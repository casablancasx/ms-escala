package br.gov.agu.nutec.msescala.service;

import br.gov.agu.nutec.msescala.dto.EscalaRequestDTO;
import br.gov.agu.nutec.msescala.entity.PautaEntity;
import br.gov.agu.nutec.msescala.enums.Uf;
import br.gov.agu.nutec.msescala.repository.PautaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class EscalaService {

    private final PautaRepository pautaRepository;
    private final RabbitTemplate rabbitTemplate;

    @Value("rabbitmq.exchange.pauta-escalar-pendente")
    private String exchange;

    @Value("rabbitmq.bindingkey.pauta-escalar")
    private String bindingKey;



    public void iniciarEscalaAvaliadores(EscalaRequestDTO request) {

        List<PautaEntity> pautas = pautaRepository.buscarPautasSemAvaliadoresEscalados(
                request.dataIncio(),
                request.dataFim(),
                request.uf());

        pautas.parallelStream().forEach(pauta -> {
            rabbitTemplate.convertAndSend(exchange, bindingKey, pauta);
        });

    }
}
