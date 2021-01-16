package br.com.brunokayser.myfood.cadastro.infraestructure.database.persistence;

import br.com.brunokayser.myfood.cadastro.dto.RestaurantDto;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface RestaurantRepository extends JpaRepository<RestaurantDto, Long> {

    Boolean existsByNameOrEmail(String name, String email);

    Boolean existsByName(String name);

    @Modifying
    @Transactional
    @Query(value = "UPDATE cadastro.tb_restaurant tbr SET tbr.name = :name , tbr.email = :email, tbr.password = :password WHERE tbr.id = :id", nativeQuery = true)
    void update(
       @Param(value = "email") String email,
       @Param(value = "name") String name,
       @Param(value = "password") String password,
       @Param(value = "id") Long id);

}
