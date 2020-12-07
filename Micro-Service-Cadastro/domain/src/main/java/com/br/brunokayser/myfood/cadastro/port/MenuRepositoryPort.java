package com.br.brunokayser.myfood.cadastro.port;

import com.br.brunokayser.myfood.cadastro.domain.Menu;
import java.util.Optional;


public interface MenuRepositoryPort {

    Menu save(Menu menu);

    Optional<Menu> findById(Long id);

    void delete(Menu menu);

}
