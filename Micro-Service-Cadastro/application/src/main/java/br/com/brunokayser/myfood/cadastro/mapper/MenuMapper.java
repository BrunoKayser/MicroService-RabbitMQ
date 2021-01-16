package br.com.brunokayser.myfood.cadastro.mapper;

import br.com.brunokayser.myfood.cadastro.dto.MenuDto;
import br.com.brunokayser.myfood.cadastro.dto.MenuInsertDto;
import br.com.brunokayser.myfood.cadastro.dto.MenuOrderDto;
import br.com.brunokayser.myfood.cadastro.dto.MenuUpdateDto;
import com.br.brunokayser.myfood.cadastro.domain.Menu;
import com.br.brunokayser.myfood.cadastro.domain.MenuInsert;
import com.br.brunokayser.myfood.cadastro.domain.MenuUpdate;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MenuMapper {

    public static MenuUpdate toDomain(MenuUpdateDto menuUpdateDto, Long id){
        return MenuUpdate
            .builder()
            .id(id)
            .name(menuUpdateDto.getName())
            .price(menuUpdateDto.getPrice())
            .restaurant(menuUpdateDto.getRestaurant())
            .build();
    }

    public static MenuUpdateDto toDto(MenuUpdate menuUpdate){
        return new ModelMapper().map(menuUpdate, MenuUpdateDto.class);
    }



    public static MenuInsertDto toDtoWithoutId(Menu menu){

        return new MenuInsertDto(
            menu.getName(),
            menu.getPrice(),
            menu.getRestaurant().getId());
    }

    public static MenuInsert toDomain(MenuInsertDto menuInsertDto){
        return new ModelMapper().map(menuInsertDto, MenuInsert.class);
    }

    public static Menu toDomain(MenuDto menuDto){
        return new ModelMapper().map(menuDto, Menu.class);
    }

    public static MenuDto toDto(Menu menu){
        return new ModelMapper().map(menu, MenuDto.class);
    }

    public static MenuOrderDto toOrderDto(Long idMenu, Long idRestaurant){

        return new MenuOrderDto(
            idMenu,
            idRestaurant);
    }
}
