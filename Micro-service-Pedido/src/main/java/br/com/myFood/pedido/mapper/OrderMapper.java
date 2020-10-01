package br.com.myFood.pedido.mapper;

import br.com.myFood.pedido.dto.OrderDto;
import br.com.myFood.pedido.entity.Order;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public static Order toEntity(OrderDto orderDto){
        return new ModelMapper().map(orderDto,Order.class);
    }

}
