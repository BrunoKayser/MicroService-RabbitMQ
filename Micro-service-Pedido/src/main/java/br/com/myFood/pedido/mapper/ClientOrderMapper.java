package br.com.myFood.pedido.mapper;


import br.com.myFood.pedido.dto.ClientOrderDto;
import br.com.myFood.pedido.entity.ClientOrder;
import org.springframework.stereotype.Component;

@Component
public class ClientOrderMapper {

    public static ClientOrder toEntity(ClientOrderDto clientOrderDto){

        return new ClientOrder(
            null,
            clientOrderDto.getIdClient());
    }

}
