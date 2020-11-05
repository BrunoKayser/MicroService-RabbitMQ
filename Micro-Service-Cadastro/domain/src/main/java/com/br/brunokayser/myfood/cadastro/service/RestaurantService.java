package com.br.brunokayser.myfood.cadastro.service;

import com.br.brunokayser.myfood.cadastro.entity.Restaurant;
import com.br.brunokayser.myfood.cadastro.repository.RestaurantRepository;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

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
