package br.com.brunokayser.myfood.cadastro.infraestructure.ampq;

import com.br.brunokayser.myfood.cadastro.domain.ClientOrderDto;
import com.br.brunokayser.myfood.cadastro.port.ClientSendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class ClientSendMessageImpl implements ClientSendMessage {

    @Value("${cadastro.rabbitmq.exchange}")
    private String exchange;

    @Value("${cadastro.client.rabbitmq.routingkey}")
    private String rountingKey;

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public ClientSendMessageImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(ClientOrderDto clientOrderDto ) {
        log.info("[CADASTRO] - Client Id: {}",clientOrderDto);
        log.info("[CADASTRO] - Exchange: {}",exchange);
        log.info("[CADASTRO] - Rounting Key: {}",rountingKey);
        rabbitTemplate.convertAndSend(exchange, rountingKey, clientOrderDto);

    }
}