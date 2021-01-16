package br.com.brunokayser.myfood.cadastro.infraestructure.database.persistence;

import br.com.brunokayser.myfood.cadastro.dto.MenuDto;
import com.br.brunokayser.myfood.cadastro.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface MenuRepository extends JpaRepository<MenuDto, Long> {

    Boolean existsByName(String name);

    @Modifying
    @Transactional
    @Query(value = "UPDATE cadastro.tb_menu tbm SET tbm.name = :name , tbm.price = :price, tbm.restaurant_id = :restaurantId WHERE tbm.id = :idMenu", nativeQuery = true)
    void update(
        @Param(value = "idMenu") Long idMenu,
        @Param(value = "name") String name,
        @Param(value = "price") Double price,
        @Param(value = "restaurantId") Long idRestaurant);
}
