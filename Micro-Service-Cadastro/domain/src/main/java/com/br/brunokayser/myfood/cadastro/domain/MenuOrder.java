package com.br.brunokayser.myfood.cadastro.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuOrder {

    private Long idMenu;
    private Long idRestaurant;

}
