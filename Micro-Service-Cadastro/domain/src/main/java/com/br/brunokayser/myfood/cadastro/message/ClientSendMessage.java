package com.br.brunokayser.myfood.cadastro.message;

import br.com.brunokayser.myfood.cadastro.dto.ClientOrderDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ClientSendMessage {

    @Value("${cadastro.rabbitmq.exchange}")
    private String exchange;

    @Value("${cadastro.client.rabbitmq.routingkey}")
    private String rountingKey;

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public ClientSendMessage(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(ClientOrderDto clientOrderDto ) {
        System.out.println(clientOrderDto);
        System.out.println(exchange);
        System.out.println(rountingKey);
        rabbitTemplate.convertAndSend(exchange, rountingKey, clientOrderDto);

    }
}