package com.br.myfood.Cadastro.mapper;

import com.br.myfood.Cadastro.dto.MenuDto;
import com.br.myfood.Cadastro.dto.MenuOrderDto;
import com.br.myfood.Cadastro.entity.Menu;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MenuMapper {

    public static Menu toEntity(MenuDto menuDto){
        return new ModelMapper().map(menuDto, Menu.class);
    }

    public static Menu toEntity(MenuDto menuDto, Long id){
        var menu = new ModelMapper().map(menuDto, Menu.class);
        menu.setId(id);
        return menu;
    }

    public static MenuDto toDto(Menu menu){

        return new MenuDto(
            menu.getName(),
            menu.getPrice(),
            menu.getRestaurant().getId());
    }

    public static MenuOrderDto toOrderDto(Long idMenu, Long idRestaurant){

        return new MenuOrderDto(
            idMenu,
            idRestaurant);
    }
}
