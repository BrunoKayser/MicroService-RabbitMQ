package com.br.brunokayser.myfood.cadastro.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class MenuInsert {

    private String name;
    private Double price;
    private Long restaurant;

}
