package com.br.myfood.Cadastro.service;

import com.br.myfood.Cadastro.dto.MenuDto;
import com.br.myfood.Cadastro.entity.Menu;
import com.br.myfood.Cadastro.mapper.MenuMapper;
import com.br.myfood.Cadastro.repository.MenuRepository;
import com.br.myfood.Cadastro.repository.RestaurantRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuService {


    private final MenuRepository menuRepository;

    private final RestaurantRepository restaurantRepository;

    @Autowired
    public MenuService(MenuRepository menuRepository, RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
        this.menuRepository = menuRepository;
    }

    public Menu insertMenu(MenuDto menudto) {
        var restaurant = restaurantRepository.findById(menudto.getRestaurant());

        if (restaurant.isPresent()) {
            var menu = MenuMapper.toEntity(menudto);
            menu.setRestaurant(restaurant.get());
            return  menuRepository.save(menu);
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
