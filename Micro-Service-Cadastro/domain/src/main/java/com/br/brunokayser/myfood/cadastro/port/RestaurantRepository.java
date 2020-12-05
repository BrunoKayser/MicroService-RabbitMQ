package com.br.brunokayser.myfood.cadastro.port;

import com.br.brunokayser.myfood.cadastro.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

}
