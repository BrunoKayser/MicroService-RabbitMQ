package com.br.brunokayser.myfood.cadastro.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Menu {

    private Long Id;
    private String name;
    private Double price;
    private Restaurant restaurant;

}
