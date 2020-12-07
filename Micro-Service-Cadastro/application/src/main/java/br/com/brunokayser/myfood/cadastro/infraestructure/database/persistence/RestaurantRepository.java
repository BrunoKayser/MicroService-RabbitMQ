package br.com.brunokayser.myfood.cadastro.infraestructure.database.persistence;

import br.com.brunokayser.myfood.cadastro.dto.RestaurantDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<RestaurantDto, Long> {

}
