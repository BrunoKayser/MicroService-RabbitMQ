package com.br.myfood.Cadastro.service;

import com.br.myfood.Cadastro.entity.Client;
import com.br.myfood.Cadastro.entity.Restaurant;
import com.br.myfood.Cadastro.repository.ClientRepository;
import com.br.myfood.Cadastro.repository.RestaurantRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Restaurant insertRestaurant(Restaurant client) {
        return restaurantRepository.save(client);
    }

    public Restaurant updateRestaurant(Restaurant restaurant) {

        var newRestaurant = restaurantRepository.findById(restaurant.getId());

        if (newRestaurant.isPresent()) {
            return restaurantRepository.save(restaurant);
        } else {
            return null;
        }
    }

    public boolean deleteRestaurant(Long id) {

        var deleteRestaurant = restaurantRepository.findById(id);

        if (deleteRestaurant.isPresent()) {
            restaurantRepository.delete(deleteRestaurant.get());
            return true;
        } else {
            return false;
        }
    }

    public Optional<Restaurant> findById(Long id) {

        return restaurantRepository.findById(id);

    }

}
