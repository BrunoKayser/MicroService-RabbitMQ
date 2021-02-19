package com.br.brunokayser.myfood.cadastro.service;


import static com.br.brunokayser.myfood.cadastro.domain.enums.Constant.TAG;
import static com.br.brunokayser.myfood.cadastro.mapper.MenuMapper.buildMenuToUpdate;
import static com.br.brunokayser.myfood.cadastro.mapper.MenuMapper.toOrderDto;

import com.br.brunokayser.myfood.cadastro.domain.Menu;
import com.br.brunokayser.myfood.cadastro.domain.MenuInsert;
import com.br.brunokayser.myfood.cadastro.domain.MenuUpdate;
import com.br.brunokayser.myfood.cadastro.domain.Restaurant;
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

        var restaurant = restaurantRepositoryPort.findById(menuInsert.getRestaurant());

        serviceValidator.validateIfFound(restaurant.isEmpty());

        serviceValidator.validateIfExistsFoodPlate(menuRepositoryPort
            .existsByName(menuInsert.getName()), restaurantRepositoryPort.existsById(menuInsert.getRestaurant()));

        var menuSaved = menuRepositoryPort.save(buildMenu(menuInsert, restaurant.get()));

        menuSendMessage.sendMessage(toOrderDto(menuSaved.getId(), menuSaved.getRestaurant().getId()));

        log.info(TAG + " Successful save menu: {}", menuSaved);
        return menuSaved;
    }

    public Menu updateMenu(MenuUpdate menuToUpdate) {

        var menuFound = menuRepositoryPort.findById(menuToUpdate.getId());

        var restaurantFound = restaurantRepositoryPort.findById(menuToUpdate.getRestaurant());

        if(menuFound.isPresent()){
            restaurantFound.ifPresent(restaurant -> setThisRestaurantInMenuFound(restaurant, menuFound.get()));
        }

        serviceValidator.validateIfFound(menuFound.isEmpty(), restaurantFound.isEmpty());

        serviceValidator.validateIfExistsFoodPlate(menuRepositoryPort
            .existsByName(menuToUpdate.getName()), restaurantRepositoryPort.existsById(restaurantFound.get().getId()));

        var menuUpdated = buildMenuToUpdate(menuToUpdate, menuFound.get());

        menuRepositoryPort.update(menuUpdated);

        log.info(TAG + " Successful update menu: {}", menuUpdated);
        return menuUpdated;
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

    private void setThisRestaurantInMenuFound(Restaurant restaurant, Menu menu) {
        menu.setRestaurant(restaurant);
    }
}
