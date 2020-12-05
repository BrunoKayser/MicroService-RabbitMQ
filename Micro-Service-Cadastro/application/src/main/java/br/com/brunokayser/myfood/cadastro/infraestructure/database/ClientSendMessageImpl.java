package br.com.brunokayser.myfood.cadastro.infraestructure.database;

import com.br.brunokayser.myfood.cadastro.domain.ClientOrderDto;
import com.br.brunokayser.myfood.cadastro.port.ClientSendMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public class ClientSendMessageImpl implements ClientSendMessage {

    @Value("${cadastro.rabbitmq.exchange}")
    private String exchange;

    @Value("${cadastro.client.rabbitmq.routingkey}")
    private String rountingKey;

    //Talvez colocar o @Autowried aqui

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public ClientSendMessageImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(ClientOrderDto clientOrderDto ) {
        System.out.println(clientOrderDto);
        System.out.println(exchange);
        System.out.println(rountingKey);
        rabbitTemplate.convertAndSend(exchange, rountingKey, clientOrderDto);

    }
}