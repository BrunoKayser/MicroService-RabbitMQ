package com.br.brunokayser.myfood.cadastro.port;

import com.br.brunokayser.myfood.cadastro.domain.Restaurant;
import java.util.Optional;


public interface RestaurantRepositoryPort {

    Restaurant save(Restaurant restaurant);

    Optional<Restaurant> findById(Long id);

    void delete(Restaurant restaurant);

    Boolean existsByEmailOrName(String email, String name);

    void update(Restaurant restaurant);

    Boolean existsById(Long name);

}
