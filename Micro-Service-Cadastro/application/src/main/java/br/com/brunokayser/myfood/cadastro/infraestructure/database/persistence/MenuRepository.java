package br.com.brunokayser.myfood.cadastro.infraestructure.database.persistence;

import br.com.brunokayser.myfood.cadastro.dto.MenuDto;
import com.br.brunokayser.myfood.cadastro.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<MenuDto, Long> {

}
