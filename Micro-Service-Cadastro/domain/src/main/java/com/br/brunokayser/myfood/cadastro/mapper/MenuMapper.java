package com.br.brunokayser.myfood.cadastro.mapper;

import com.br.brunokayser.myfood.cadastro.domain.Menu;
import com.br.brunokayser.myfood.cadastro.domain.MenuOrder;
import com.br.brunokayser.myfood.cadastro.domain.MenuUpdate;
import java.util.Optional;

public class MenuMapper {

    public static MenuOrder toOrderDto(Long idMenu, Long idRestaurant){

        return MenuOrder
            .builder()
            .idMenu(idMenu)
            .idRestaurant(idRestaurant)
            .build();
    }

    public static Menu buildMenuToUpdate(MenuUpdate menuUpdate, Menu menu){

        return Menu
            .builder()
            .Id(menuUpdate.getId())
            .name(Optional.ofNullable(menuUpdate.getName()).orElse(menu.getName()))
            .price(Optional.ofNullable(menuUpdate.getPrice()).orElse(menu.getPrice()))
            .restaurant(menu.getRestaurant())
            .build();
    }
}
