package br.com.brunokayser.myfood.cadastro.mapper;

import br.com.brunokayser.myfood.cadastro.dto.MenuInsertDto;
import br.com.brunokayser.myfood.cadastro.dto.MenuOrderDto;
import com.br.brunokayser.myfood.cadastro.domain.Menu;
import com.br.brunokayser.myfood.cadastro.domain.MenuInsert;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MenuMapper {

    public static Menu toEntity(MenuInsertDto menuInsertDto, Long id){
        var menu = new ModelMapper().map(menuInsertDto, Menu.class);
        menu.setId(id);
        return menu;
    }

    public static MenuInsertDto toDto(Menu menu){

        return new MenuInsertDto(
            menu.getName(),
            menu.getPrice(),
            menu.getRestaurant().getId());
    }

    public static MenuInsert toDomain(MenuInsertDto menuInsertDto){
        return new ModelMapper().map(menuInsertDto, MenuInsert.class);

    }

    public static MenuOrderDto toOrderDto(Long idMenu, Long idRestaurant){

        return new MenuOrderDto(
            idMenu,
            idRestaurant);
    }
}
