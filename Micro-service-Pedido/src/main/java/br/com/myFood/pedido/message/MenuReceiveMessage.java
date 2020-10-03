package br.com.myFood.pedido.message;

import br.com.myFood.pedido.dto.ClientOrderDto;
import br.com.myFood.pedido.dto.MenuOrderDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class MenuReceiveMessage {

    @RabbitListener(queues = {"${cadastro.menu.rabbitmq.queue}"})
    public void receiveMessage(@Payload MenuOrderDto menuOrderDto){

        System.out.println(menuOrderDto);
    }

}
