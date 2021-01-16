package com.br.brunokayser.myfood.cadastro.mapper;

import static java.util.Optional.ofNullable;

import com.br.brunokayser.myfood.cadastro.domain.Client;

public class ClientMapper {

    public static Client buildClientToUpdate(Client update, Client found){

        return Client
            .builder()
            .id(update.getId())
            .email(ofNullable(update.getEmail()).orElse(found.getEmail()))
            .name(ofNullable(update.getName()).orElse(found.getName()))
            .password(ofNullable(update.getPassword()).orElse(found.getPassword()))
            .build();
    }

}
