package com.br.myfood.Cadastro.dto;

import javax.persistence.SecondaryTable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MenuOrderDto {

    private Long idMenu;
    private Long idRestaurante;

}
