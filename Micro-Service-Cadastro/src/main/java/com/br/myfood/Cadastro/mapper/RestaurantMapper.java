package com.br.myfood.Cadastro.mapper;

import com.br.myfood.Cadastro.dto.RestaurantDto;
import com.br.myfood.Cadastro.entity.Restaurant;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RestaurantMapper {

    public static Restaurant toEntity(RestaurantDto restaurantDto){

        return new ModelMapper().map(restaurantDto, Restaurant.class);
    }

    public static Restaurant toEntity(RestaurantDto restaurantDto,Long id){

        var restaurant = new ModelMapper().map(restaurantDto, Restaurant.class);
        restaurant.setId(id);
        return restaurant;

    }

}
