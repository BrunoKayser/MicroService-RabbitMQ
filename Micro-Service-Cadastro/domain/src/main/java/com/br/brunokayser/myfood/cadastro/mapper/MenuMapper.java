package com.br.brunokayser.myfood.cadastro.mapper;

import static java.util.Optional.ofNullable;

import com.br.brunokayser.myfood.cadastro.domain.Client;
import com.br.brunokayser.myfood.cadastro.domain.MenuOrder;
import com.br.brunokayser.myfood.cadastro.domain.Restaurant;

public class MenuMapper {

    public static MenuOrder toOrderDto(Long idMenu, Long idRestaurant){

        return MenuOrder
            .builder()
            .idMenu(idMenu)
            .idRestaurant(idRestaurant)
            .build();
    }
}
