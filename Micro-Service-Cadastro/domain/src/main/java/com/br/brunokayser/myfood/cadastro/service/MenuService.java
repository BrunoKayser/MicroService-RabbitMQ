package com.br.brunokayser.myfood.cadastro.service;

//import br.com.brunokayser.myfood.cadastro.dto.MenuDto;
//import br.com.brunokayser.myfood.cadastro.mapper.MenuMapper;

import com.br.brunokayser.myfood.cadastro.domain.Menu;
import com.br.brunokayser.myfood.cadastro.domain.MenuInsert;
import com.br.brunokayser.myfood.cadastro.domain.Restaurant;
import com.br.brunokayser.myfood.cadastro.mapper.MenuMapper;
import com.br.brunokayser.myfood.cadastro.port.MenuRepositoryPort;
import com.br.brunokayser.myfood.cadastro.port.MenuSendMessage;
import com.br.brunokayser.myfood.cadastro.port.RestaurantRepositoryPort;
import java.util.Optional;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class MenuService {

    private final MenuRepositoryPort menuRepositoryPort;
    private final RestaurantRepositoryPort restaurantRepositoryPort;
    private final MenuSendMessage menuSendMessage;

    public Menu insertMenu(MenuInsert menuInsert) {
        var restaurant = restaurantRepositoryPort.findById(menuInsert.getRestaurant());

        if (restaurant.isPresent()) {
            var menu = menuInsert;
            menu.setRestaurant(restaurant.get().getId());
            var newMenu = menuRepositoryPort.save(buildMenu(menuInsert, restaurant.get()));
            menuSendMessage.sendMessage(MenuMapper.toOrderDto(newMenu.getId(), newMenu.getRestaurant().getId()));
            return newMenu;

        } else {
            return null;
        }
    }

    public Menu updateMenu(Menu menu) {

        var newMenu = menuRepositoryPort.findById(menu.getId());

        if (newMenu.isPresent()) {
            return menuRepositoryPort.save(menu);
        } else {
            return null;
        }
    }

    public boolean deleteMenu(Long id) {

        var deleteMenu = menuRepositoryPort.findById(id);

        if (deleteMenu.isPresent()) {
            menuRepositoryPort.delete(deleteMenu.get());
            return true;
        } else {
            return false;
        }
    }

    public Optional<Menu> findById(Long id) {

        return menuRepositoryPort.findById(id);

    }

    private Menu buildMenu(MenuInsert menuInsert, Restaurant restaurant){

        return Menu
            .builder()
            .name(menuInsert.getName())
            .price(menuInsert.getPrice())
            .restaurant(restaurant)
            .build();
    }
}
