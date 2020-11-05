package com.br.brunokayser.myfood.cadastro.service;

import br.com.brunokayser.myfood.cadastro.dto.MenuDto;
import br.com.brunokayser.myfood.cadastro.mapper.MenuMapper;
import com.br.brunokayser.myfood.cadastro.entity.Menu;
import com.br.brunokayser.myfood.cadastro.message.MenuSendMessage;
import com.br.brunokayser.myfood.cadastro.repository.MenuRepository;
import com.br.brunokayser.myfood.cadastro.repository.RestaurantRepository;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuSendMessage menuSendMessage;

    public Menu insertMenu(MenuDto menudto) {
        var restaurant = restaurantRepository.findById(menudto.getRestaurant());

        if (restaurant.isPresent()) {
            var menu = MenuMapper.toEntity(menudto);
            menu.setRestaurant(restaurant.get());
            var newMenu =  menuRepository.save(menu);
            menuSendMessage.sendMessage(MenuMapper.toOrderDto(newMenu.getId(), newMenu.getRestaurant().getId()));
            return newMenu;

        } else{
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

}
