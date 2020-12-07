package br.com.brunokayser.myfood.cadastro.infraestructure.ampq;

import com.br.brunokayser.myfood.cadastro.domain.LoginDto;
import com.br.brunokayser.myfood.cadastro.port.LoginSendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class LoginSendMessageImpl implements LoginSendMessage {


    @Value("${cadastro.rabbitmq.exchange}")
    private String exchange;

    @Value("${cadastro.login.rabbitmq.routingkey}")
    private String rountingKey;

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public LoginSendMessageImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(LoginDto loginDto) {
        log.info("[CADASTRO] - Login Id: {}",loginDto);
        log.info("[CADASTRO] - Exchange: {}",exchange);
        log.info("[CADASTRO] - Rounting Key: {}",rountingKey);
        rabbitTemplate.convertAndSend(exchange, rountingKey, loginDto);

    }
}
