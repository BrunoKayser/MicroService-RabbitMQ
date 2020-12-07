package com.br.brunokayser.myfood.cadastro.service;

import com.br.brunokayser.myfood.cadastro.domain.Restaurant;
import com.br.brunokayser.myfood.cadastro.port.RestaurantRepositoryPort;
import java.util.Optional;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepositoryPort restaurantRepositoryPort;

    public Restaurant insertRestaurant(Restaurant restaurant) {
        return restaurantRepositoryPort.save(restaurant);
    }

    public Restaurant updateRestaurant(Restaurant restaurant) {

        var newRestaurant = restaurantRepositoryPort.findById(restaurant.getId());

        if (newRestaurant.isPresent()) {
            return restaurantRepositoryPort.save(restaurant);
        } else {
            return null;
        }
    }

    public boolean deleteRestaurant(Long id) {

        var deleteRestaurant = restaurantRepositoryPort.findById(id);

        if (deleteRestaurant.isPresent()) {
            restaurantRepositoryPort.delete(deleteRestaurant.get());
            return true;
        } else {
            return false;
        }
    }

    public Optional<Restaurant> findById(Long id) {

        return restaurantRepositoryPort.findById(id);

    }
}
