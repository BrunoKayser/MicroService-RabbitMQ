package com.br.brunokayser.myfood.cadastro.message;


import br.com.brunokayser.myfood.cadastro.dto.MenuOrderDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MenuSendMessage {

    @Value("${cadastro.rabbitmq.exchange}")
    private String exchange;

    @Value("${cadastro.menu.rabbitmq.routingkey}")
    private String rountingKey;

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public MenuSendMessage(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(MenuOrderDto menuOrderDto) {
        System.out.println(menuOrderDto);
        System.out.println(exchange);
        System.out.println(rountingKey);
        rabbitTemplate.convertAndSend(exchange, rountingKey, menuOrderDto);

    }
}