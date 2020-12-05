package br.com.brunokayser.myfood.cadastro.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MenuInsertDto {

    private String name;
    private Double price;
    private Long restaurant;

}
