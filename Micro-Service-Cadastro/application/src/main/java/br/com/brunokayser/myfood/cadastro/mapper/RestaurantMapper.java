package br.com.brunokayser.myfood.cadastro.mapper;


import br.com.brunokayser.myfood.cadastro.dto.RestaurantDto;
import com.br.brunokayser.myfood.cadastro.domain.Restaurant;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RestaurantMapper {

    public static Restaurant toDomain(RestaurantDto restaurantDto){

        return  (Optional.ofNullable(restaurantDto).isEmpty()) ? null : new ModelMapper().map(restaurantDto, Restaurant.class);
    }

    public static RestaurantDto toDto(Restaurant restaurant){
        return new ModelMapper().map(restaurant, RestaurantDto.class);
    }

    public static Restaurant toEntity(RestaurantDto restaurantDto,Long id){
        var restaurant = new ModelMapper().map(restaurantDto, Restaurant.class);
        restaurant.setId(id);
        return restaurant;
    }
}
