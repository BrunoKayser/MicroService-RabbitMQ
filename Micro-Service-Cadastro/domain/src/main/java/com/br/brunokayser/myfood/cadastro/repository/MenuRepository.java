package com.br.brunokayser.myfood.cadastro.repository;

import com.br.brunokayser.myfood.cadastro.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

}
