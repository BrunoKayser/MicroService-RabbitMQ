package com.br.brunokayser.myfood.cadastro.service;


import static com.br.brunokayser.myfood.cadastro.domain.enums.Constant.TAG;
import static com.br.brunokayser.myfood.cadastro.mapper.MenuMapper.toOrderDto;

import com.br.brunokayser.myfood.cadastro.domain.Menu;
import com.br.brunokayser.myfood.cadastro.domain.MenuInsert;
import com.br.brunokayser.myfood.cadastro.domain.MenuUpdate;
import com.br.brunokayser.myfood.cadastro.domain.Restaurant;
import com.br.brunokayser.myfood.cadastro.mapper.MenuMapper;
import com.br.brunokayser.myfood.cadastro.port.MenuRepositoryPort;
import com.br.brunokayser.myfood.cadastro.port.MenuSendMessage;
import com.br.brunokayser.myfood.cadastro.port.RestaurantRepositoryPort;
import com.br.brunokayser.myfood.cadastro.validator.ServiceValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepositoryPort menuRepositoryPort;
    private final RestaurantRepositoryPort restaurantRepositoryPort;
    private final MenuSendMessage menuSendMessage;

    private final ServiceValidator serviceValidator;

    public Menu insertMenu(MenuInsert menuInsert) {

        //TODO: Colocar validação para ver se o prato ja existe a esse restaurante

        var restaurant = restaurantRepositoryPort.findById(menuInsert.getRestaurant());

        serviceValidator.validateIfFound(restaurant.isEmpty());

        var menuSaved = menuRepositoryPort.save(buildMenu(menuInsert, restaurant.get()));

        menuSendMessage.sendMessage(toOrderDto(menuSaved.getId(), menuSaved.getRestaurant().getId()));

        log.info(TAG + " Successful save menu: {}", menuSaved);
        return menuSaved;
    }

    public Menu updateMenu(MenuUpdate menu) {
        //TODO: Colocar validação para ver se o prato ja existe a esse restaurante, também precisa fazer o método de update no repository
        // e por último fazer o mapper para não obter valores nulos

        var newMenu = menuRepositoryPort.findById(menu.getId());

        serviceValidator.validateIfFound(newMenu.isEmpty());

        log.info(TAG + " Successful update menu: {}", menu);
        //menuRepositoryPort.update(menu);
        return null;
    }

    public void deleteMenu(Long id) {

        var deleteMenu = menuRepositoryPort.findById(id);

        serviceValidator.validateIfFound(deleteMenu.isEmpty());

        log.info(TAG + " Successful delete for client: {}", deleteMenu);
        menuRepositoryPort.delete(deleteMenu.get());

    }

    public Menu findById(Long id) {

        var menu = menuRepositoryPort.findById(id);

        serviceValidator.validateIfFound(menu.isEmpty());

        log.info(TAG + "Get Successful menu: {}", menu.get());

        return menu.get();

    }

    private Menu buildMenu(MenuInsert menuInsert, Restaurant restaurant) {

        return Menu
            .builder()
            .name(menuInsert.getName())
            .price(menuInsert.getPrice())
            .restaurant(restaurant)
            .build();
    }

}
