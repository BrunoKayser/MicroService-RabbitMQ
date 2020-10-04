package br.com.myFood.pedido.repository;

import br.com.myFood.pedido.entity.MenuOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuOrderRepository extends JpaRepository<MenuOrder, Long> {

}
