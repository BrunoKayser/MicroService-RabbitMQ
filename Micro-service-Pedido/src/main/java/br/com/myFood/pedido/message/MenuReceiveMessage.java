package br.com.myFood.pedido.message;

import br.com.myFood.pedido.dto.MenuOrderDto;
import br.com.myFood.pedido.mapper.MenuOrderMapper;
import br.com.myFood.pedido.repository.MenuOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MenuReceiveMessage {

    private final MenuOrderRepository menuOrderRepository;

    public MenuReceiveMessage(MenuOrderRepository menuOrderRepository) {
        this.menuOrderRepository = menuOrderRepository;
    }

    @RabbitListener(queues = {"${cadastro.menu.rabbitmq.queue}"})
    public void receiveMessage(@Payload MenuOrderDto menuOrderDto){

        menuOrderRepository.save(MenuOrderMapper.toEntity(menuOrderDto));
        log.info("SUCCESFULL SAVE {}", menuOrderDto);
    }

}
