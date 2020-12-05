package com.br.brunokayser.myfood.cadastro.service;

//import br.com.brunokayser.myfood.cadastro.dto.MenuDto;
//import br.com.brunokayser.myfood.cadastro.mapper.MenuMapper;

import com.br.brunokayser.myfood.cadastro.domain.Menu;
import com.br.brunokayser.myfood.cadastro.domain.MenuInsert;
import com.br.brunokayser.myfood.cadastro.domain.Restaurant;
import com.br.brunokayser.myfood.cadastro.mapper.MenuMapper;
import com.br.brunokayser.myfood.cadastro.port.MenuRepository;
import com.br.brunokayser.myfood.cadastro.port.MenuSendMessage;
import com.br.brunokayser.myfood.cadastro.port.RestaurantRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

//@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuSendMessage menuSendMessage;

    public Menu insertMenu(MenuInsert menuInsert) {
        var restaurant = restaurantRepository.findById(menuInsert.getRestaurant());

        if (restaurant.isPresent()) {
            var menu = menuInsert;
            menu.setRestaurant(restaurant.get().getId());
            var newMenu = menuRepository.save(buildMenu(menuInsert, restaurant.get()));
            menuSendMessage.sendMessage(MenuMapper.toOrderDto(newMenu.getId(), newMenu.getRestaurant().getId()));
            return newMenu;

        } else {
            return null;
        }
    }

    public Menu updateMenu(Menu menu) {

        var newMenu = menuRepository.findById(menu.getId());

        if (newMenu.isPresent()) {
            return menuRepository.save(menu);
        } else {
            return null;
        }
    }

    public boolean deleteMenu(Long id) {

        var deleteMenu = menuRepository.findById(id);

        if (deleteMenu.isPresent()) {
            menuRepository.delete(deleteMenu.get());
            return true;
        } else {
            return false;
        }
    }

    public Optional<Menu> findById(Long id) {

        return menuRepository.findById(id);

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
