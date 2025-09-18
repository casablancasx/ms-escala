package br.gov.agu.nutec.msescala.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.exchange.pauta-escalar-pendente}")
    private String exchangeEscala;

    @Value("${rabbitmq.queue.pauta-ms-escalar}")
    private String filaPautaMsEscalar;
    
    @Value("${rabbitmq.queue.escala-pendente-avaliador}")
    private String filaEscalaPendenteAvaliador;
    
    @Value("${rabbitmq.queue.escala-pendente-pautista}")
    private String filaEscalaPendentePautista;
    
    @Value("${rabbitmq.bindingkey.avaliador}")
    private String bindingKeyAvaliador;
    
    @Value("${rabbitmq.bindingkey.pautista}")
    private String bindingKeyPautista;


    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public DirectExchange directExchange(){
        return ExchangeBuilder.directExchange(exchangeEscala).build();
    }

    @Bean
    public Queue escalarPautas(){
        return QueueBuilder.durable(filaPautaMsEscalar).build();
    }
    
    @Bean
    public Queue escalarPendentesAvaliador(){
        return QueueBuilder.durable(filaEscalaPendenteAvaliador).build();
    }
    
    @Bean
    public Queue escalarPendentesPautista(){
        return QueueBuilder.durable(filaEscalaPendentePautista).build();
    }

    @Bean
    public Binding escalarPautasBinding(){
        return BindingBuilder.bind(escalarPautas()).to(directExchange()).with("");
    }
    
    @Bean
    public Binding escalarPendentesAvaliadorBinding(){
        return BindingBuilder.bind(escalarPendentesAvaliador()).to(directExchange()).with(bindingKeyAvaliador);
    }
    
    @Bean
    public Binding escalarPendentesPautistaBinding(){
        return BindingBuilder.bind(escalarPendentesPautista()).to(directExchange()).with(bindingKeyPautista);
    }

}
