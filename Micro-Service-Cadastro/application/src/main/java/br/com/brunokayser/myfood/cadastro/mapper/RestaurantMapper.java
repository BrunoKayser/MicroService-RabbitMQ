package br.com.brunokayser.myfood.cadastro.mapper;


import br.com.brunokayser.myfood.cadastro.dto.RestaurantDto;
import com.br.brunokayser.myfood.cadastro.entity.Restaurant;
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