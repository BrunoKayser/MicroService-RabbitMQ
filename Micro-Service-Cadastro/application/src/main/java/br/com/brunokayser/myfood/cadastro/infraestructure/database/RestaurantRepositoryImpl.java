package br.com.brunokayser.myfood.cadastro.infraestructure.database;

import static br.com.brunokayser.myfood.cadastro.mapper.RestaurantMapper.toDomain;
import static br.com.brunokayser.myfood.cadastro.mapper.RestaurantMapper.toDto;

import br.com.brunokayser.myfood.cadastro.infraestructure.database.persistence.RestaurantRepository;
import com.br.brunokayser.myfood.cadastro.domain.Restaurant;
import com.br.brunokayser.myfood.cadastro.port.RestaurantRepositoryPort;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RestaurantRepositoryImpl implements RestaurantRepositoryPort {

    private final RestaurantRepository restaurantRepository;

    @Override
    public Restaurant save(Restaurant restaurant) {
        return toDomain(restaurantRepository.save(toDto(restaurant)));
    }

    @Override
    public Optional<Restaurant> findById(Long id) {
        return Optional.of(toDomain(restaurantRepository.findById(id).get()));
    }

    @Override
    public void delete(Restaurant restaurant) {
        restaurantRepository.delete(toDto(restaurant));
    }
}
