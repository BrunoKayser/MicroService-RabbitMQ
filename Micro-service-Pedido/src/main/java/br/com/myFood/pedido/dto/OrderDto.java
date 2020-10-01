package br.com.myFood.pedido.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Long id;
    private Long idClient;
    private Long idRestaurant;
    private Long idMenu;

}
