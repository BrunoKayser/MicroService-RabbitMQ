package com.br.brunokayser.myfood.cadastro.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Client implements Serializable {

    private Long id;
    private String name;
    private String email;
    private String password;

}
