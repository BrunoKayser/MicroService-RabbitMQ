package br.com.myFood.pedido.message;

import br.com.myFood.pedido.dto.ClientOrderDto;
import br.com.myFood.pedido.mapper.ClientOrderMapper;
import br.com.myFood.pedido.repository.ClientOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ClientReceiveMessage {

    private final ClientOrderRepository clientOrderRepository;

    @Autowired
    public ClientReceiveMessage(ClientOrderRepository clientOrderRepository) {
        this.clientOrderRepository = clientOrderRepository;
    }

    @RabbitListener(queues = {"${cadastro.client.rabbitmq.queue}"})
    public void receiveMessage(@Payload ClientOrderDto clientOrderDto){

        clientOrderRepository.save(ClientOrderMapper.toEntity(clientOrderDto));

        log.info("[PEDIDO] - SUCCESFULL SAVE {}", clientOrderDto);

    }
}
