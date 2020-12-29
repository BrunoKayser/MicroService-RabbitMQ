package br.com.brunokayser.myfood.cadastro.infraestructure.database;

import static br.com.brunokayser.myfood.cadastro.mapper.RestaurantMapper.toDomain;
import static br.com.brunokayser.myfood.cadastro.mapper.RestaurantMapper.toDto;
import static java.util.Optional.ofNullable;

import br.com.brunokayser.myfood.cadastro.infraestructure.database.persistence.RestaurantRepository;
import com.br.brunokayser.myfood.cadastro.domain.Restaurant;
import com.br.brunokayser.myfood.cadastro.port.RestaurantRepositoryPort;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class RestaurantRepositoryPortImpl implements RestaurantRepositoryPort {

    private final RestaurantRepository restaurantRepository;

    @Override
    public Restaurant save(Restaurant restaurant) {
        return toDomain(restaurantRepository.save(toDto(restaurant)));
    }

    @Override
    public Optional<Restaurant> findById(Long id) {

        var restaurant = restaurantRepository.findById(id);

        if (restaurant.isEmpty()) {
            return ofNullable(toDomain(null));
        }

        return ofNullable(toDomain(restaurant.get()));
    }

    @Override
    public void delete(Restaurant restaurant) {
        restaurantRepository.delete(toDto(restaurant));
    }

    @Override
    public Boolean existsByEmailOrName(String email, String name) {
        return restaurantRepository.existsByNameOrEmail(name, email);
    }

    @Override
    public void update(Restaurant restaurant) {
        restaurantRepository.update(restaurant.getEmail(), restaurant.getName(), restaurant.getPassword(), restaurant.getId());
    }
}
