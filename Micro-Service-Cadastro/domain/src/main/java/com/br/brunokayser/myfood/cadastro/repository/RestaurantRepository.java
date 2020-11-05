package com.br.brunokayser.myfood.cadastro.repository;

import com.br.brunokayser.myfood.cadastro.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

}
