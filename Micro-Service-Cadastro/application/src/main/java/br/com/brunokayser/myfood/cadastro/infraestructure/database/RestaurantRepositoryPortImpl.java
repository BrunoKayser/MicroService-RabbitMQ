package br.com.brunokayser.myfood.cadastro.infraestructure.database;

import static br.com.brunokayser.myfood.cadastro.mapper.RestaurantMapper.toDomain;
import static br.com.brunokayser.myfood.cadastro.mapper.RestaurantMapper.toDto;
import static java.util.Optional.ofNullable;

import br.com.brunokayser.myfood.cadastro.infraestructure.database.persistence.RestaurantRepository;
import com.br.brunokayser.myfood.cadastro.domain.Restaurant;
import com.br.brunokayser.myfood.cadastro.exception.InternalErrorException;
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
        try {
            return toDomain(restaurantRepository.save(toDto(restaurant)));
        } catch (Exception e) {
            log.error("Error in repository trying to save, restaurant: {}, stackTrace{}", restaurant, e.fillInStackTrace());
            throw new InternalErrorException("error.restaurant.repository");
        }
    }

    @Override
    public Optional<Restaurant> findById(Long id) {
        try {
            if (id == null) {
                id = 0L;
            }

            var restaurant = restaurantRepository.findById(id);

            if (restaurant.isEmpty()) {
                return Optional.empty();
            }

            return ofNullable(toDomain(restaurant.get()));
        } catch (Exception e) {
            log.error("Error in repository trying to find restaurant, id: {}, stackTrace{}", id, e.fillInStackTrace());
            throw new InternalErrorException("error.restaurant.repository");
        }
    }

    @Override
    public void delete(Restaurant restaurant) {
        try {
            restaurantRepository.delete(toDto(restaurant));
        } catch (Exception e) {
            log.error("Error in repository trying to delete restaurant, restaurant: {}, stackTrace{}", restaurant, e.fillInStackTrace());
            throw new InternalErrorException("error.restaurant.repository");
        }
    }

    @Override
    public Boolean existsByEmailOrName(String email, String name) {
        try {
            return restaurantRepository.existsByNameOrEmail(name, email);
        } catch (Exception e) {
            log.error("Error in Restaurant repository trying to verify if exists by name {} and email {}, stackTrace{}",email, name, e.fillInStackTrace());
            throw new InternalErrorException("error.restaurant.repository");
        }
    }

    @Override
    public void update(Restaurant restaurant) {
        try {
            restaurantRepository.update(restaurant.getEmail(), restaurant.getName(), restaurant.getPassword(), restaurant.getId());
        } catch (Exception e) {
            log.error("Error in Restaurant repository trying to update restaurant: {}, stackTrace{}", restaurant, e.fillInStackTrace());
            throw new InternalErrorException("error.restaurant.repository");
        }
    }

    @Override
    public Boolean existsById(Long id) {
        try {
            return restaurantRepository.existsById(id);
        } catch (Exception e) {
            log.error("Error in Restaurant repository trying to verify if exist by id: {}, stackTrace{}", id, e.fillInStackTrace());
            throw new InternalErrorException("error.restaurant.repository");
        }
    }
}
