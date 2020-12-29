package com.br.brunokayser.myfood.cadastro.service;


import com.br.brunokayser.myfood.cadastro.domain.Menu;
import com.br.brunokayser.myfood.cadastro.domain.MenuInsert;
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

    private static final String TAG = "CADASTRO - ";

    public Menu insertMenu(MenuInsert menuInsert) {

        //TODO validações dos dados do menuInsert serão colocadas na controller

        var restaurant = restaurantRepositoryPort.findById(menuInsert.getRestaurant());

        serviceValidator.validateIfFound(restaurant.isEmpty());

        menuInsert.setRestaurant(restaurant.get().getId());

        var newMenu = menuRepositoryPort.save(buildMenu(menuInsert, restaurant.get()));

        menuSendMessage.sendMessage(MenuMapper.toOrderDto(newMenu.getId(), newMenu.getRestaurant().getId()));
        log.info(TAG + " Successful save menu: {}", newMenu);
        return newMenu;

    }

    public Menu updateMenu(Menu menu) {

        //TODO: Falta ajustar o Update para dar um update e não um save

        var newMenu = menuRepositoryPort.findById(menu.getId());

        serviceValidator.validateIfFound(newMenu.isEmpty());

        log.info(TAG + " Successful update menu: {}", menu);
        return menuRepositoryPort.save(menu);

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
