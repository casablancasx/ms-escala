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

    @Value("${rabbitmq.exchange.pauta-escalar-pendente}}")
    private String filaEscalarPautaPendente;


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
        return QueueBuilder.durable(filaEscalarPautaPendente).build();
    }

    @Bean
    public Binding escalarPautaPendenteBinding(){
        return BindingBuilder.bind(escalarPautas()).to(directExchange()).with("");
    }

}
