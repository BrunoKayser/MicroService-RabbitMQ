package br.com.brunokayser.myfood.cadastro.configuration;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {

    @Value("${cadastro.rabbitmq.exchange}")
    private String exchange;

    @Bean
    public Exchange getExchange(){
        return ExchangeBuilder
            .directExchange(exchange)
            .durable(true)
            .build();
    }

    @Bean
    @Autowired
    public RabbitTemplate getTemplate(ConnectionFactory connectionFactory){
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;

    }
}
