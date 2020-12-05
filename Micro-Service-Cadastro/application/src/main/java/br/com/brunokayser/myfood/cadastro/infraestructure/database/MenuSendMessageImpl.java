package br.com.brunokayser.myfood.cadastro.infraestructure.database;


//import br.com.brunokayser.myfood.cadastro.dto.MenuOrderDto;
import com.br.brunokayser.myfood.cadastro.domain.MenuOrder;
import com.br.brunokayser.myfood.cadastro.port.MenuSendMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public class MenuSendMessageImpl implements MenuSendMessage {

    @Value("${cadastro.rabbitmq.exchange}")
    private String exchange;

    @Value("${cadastro.menu.rabbitmq.routingkey}")
    private String rountingKey;

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public MenuSendMessageImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(MenuOrder menuOrderDto) {
        System.out.println(menuOrderDto);
        System.out.println(exchange);
        System.out.println(rountingKey);
        rabbitTemplate.convertAndSend(exchange, rountingKey, menuOrderDto);

    }
}