package br.com.myFood.pedido.mapper;

import br.com.myFood.pedido.dto.MenuOrderDto;
import br.com.myFood.pedido.entity.MenuOrder;
import org.springframework.stereotype.Component;

@Component
public class MenuOrderMapper {

    public static MenuOrder toEntity(MenuOrderDto menuOrderDto){

        return new MenuOrder(
            null,
            menuOrderDto.getIdMenu(),
            menuOrderDto.getIdRestaurant());
    }

}
