package br.com.myFood.pedido.message;

import br.com.myFood.pedido.dto.ClientOrderDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ClienteReceiveMessage {

    @RabbitListener(queues = {"${cadastro.client.rabbitmq.queue}"})
    public void receiveMessage(@Payload ClientOrderDto clientOrderDto){
        System.out.println(clientOrderDto);
    }

}
