package com.br.brunokayser.myfood.cadastro.service;

import static com.br.brunokayser.myfood.cadastro.domain.enums.Constant.TAG;
import static com.br.brunokayser.myfood.cadastro.mapper.RestaurantMapper.toDomainWithoutNullValue;

import com.br.brunokayser.myfood.cadastro.domain.Restaurant;
import com.br.brunokayser.myfood.cadastro.port.RestaurantRepositoryPort;
import com.br.brunokayser.myfood.cadastro.validator.ServiceValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepositoryPort restaurantRepositoryPort;
    private final ServiceValidator serviceValidator;

    public Restaurant insertRestaurant(Restaurant restaurant) {

        serviceValidator.verifyIfExistsNameOrEmail(
            restaurantRepositoryPort.existsByEmailOrName(restaurant.getEmail(), restaurant.getName()));

        log.info(TAG + " Successful insert restaurant: {}", restaurant);
        return restaurantRepositoryPort.save(restaurant);
    }

    public Restaurant updateRestaurant(Restaurant restaurant) {

        var restaurantSearched = restaurantRepositoryPort.findById(restaurant.getId());

        serviceValidator.validateIfFound(restaurantSearched.isEmpty());
        serviceValidator.verifyIfExistsNameOrEmail(
            restaurantRepositoryPort.existsByEmailOrName(restaurant.getEmail(), restaurant.getName()));

        restaurantRepositoryPort.update(toDomainWithoutNullValue(restaurant, restaurantSearched.get()));

        log.info(TAG + " Successful updated restaurant: {}", restaurant);
        return restaurant;
    }

    public void deleteRestaurant(Long id) {

        var restaurantToDelete = restaurantRepositoryPort.findById(id);

        serviceValidator.validateIfFound(restaurantToDelete.isEmpty());

        restaurantRepositoryPort.delete(restaurantToDelete.get());

        log.info(TAG + " Successful delete for restaurant: {}", restaurantToDelete.get());
    }

    public Restaurant findById(Long id) {

        var restaurant = restaurantRepositoryPort.findById(id);

        serviceValidator.validateIfFound(restaurant.isEmpty());

        log.info(TAG + "Get Successful restaurant : {}", restaurant.get());
        return restaurant.get();
    }
}
