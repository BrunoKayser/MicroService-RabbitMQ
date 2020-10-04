package br.com.myFood.pedido.repository;

import br.com.myFood.pedido.entity.ClientOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientOrderRepository extends JpaRepository<ClientOrder, Long> {

}
