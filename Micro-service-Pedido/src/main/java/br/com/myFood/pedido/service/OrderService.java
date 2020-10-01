package br.com.myFood.pedido.service;

import br.com.myFood.pedido.entity.Order;
import br.com.myFood.pedido.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order inserOrder(Order order){
        return orderRepository.save(order);
    }

}
