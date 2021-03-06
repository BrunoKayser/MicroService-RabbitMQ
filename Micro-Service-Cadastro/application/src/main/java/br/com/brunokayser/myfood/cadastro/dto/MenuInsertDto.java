package br.com.brunokayser.myfood.cadastro.dto;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class MenuInsertDto {

    @NotNull
    private String name;

    @NotNull
    private Double price;

    @NotNull
    private Long restaurant;

}
