package br.com.brunokayser.myfood.cadastro.infraestructure.database;

import com.br.brunokayser.myfood.cadastro.domain.LoginDto;
import com.br.brunokayser.myfood.cadastro.port.LoginSendMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
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
        System.out.println(loginDto);
        System.out.println(exchange);
        System.out.println(rountingKey);
        rabbitTemplate.convertAndSend(exchange, rountingKey, loginDto);

    }
}
