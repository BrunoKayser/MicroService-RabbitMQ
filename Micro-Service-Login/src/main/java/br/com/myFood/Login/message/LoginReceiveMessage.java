package br.com.myFood.Login.message;

import br.com.myFood.Login.dto.LoginDto;
import br.com.myFood.Login.mapper.LoginMapper;
import br.com.myFood.Login.repository.LoginRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LoginReceiveMessage {

    private final LoginRepository loginRepository;

    @Autowired
    public LoginReceiveMessage(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    @RabbitListener(queues = {"${cadastro.login.rabbitmq.queue}"})
    public void receiveMessage(@Payload LoginDto loginDto){

        var login = loginRepository.save(LoginMapper.toEntity(loginDto));

        log.info("SUCCESFULL SAVE LOGIN {}", login);

    }
}
