package com.br.brunokayser.myfood.cadastro.mapper;

import com.br.brunokayser.myfood.cadastro.domain.MenuOrder;

public class MenuMapper {

    public static MenuOrder toOrderDto(Long idMenu, Long idRestaurant){

        return MenuOrder
            .builder()
            .idMenu(idMenu)
            .idRestaurant(idRestaurant)
            .build();
    }
}
