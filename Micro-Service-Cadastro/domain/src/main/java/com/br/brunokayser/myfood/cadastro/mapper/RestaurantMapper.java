package com.br.brunokayser.myfood.cadastro.mapper;

import static java.util.Optional.ofNullable;

import com.br.brunokayser.myfood.cadastro.domain.Restaurant;


public class RestaurantMapper {

    public static Restaurant toDomainWithoutNullValue(Restaurant update, Restaurant found){

        return Restaurant
            .builder()
            .id(update.getId())
            .email(ofNullable(update.getEmail()).orElse(found.getEmail()))
            .name(ofNullable(update.getName()).orElse(found.getName()))
            .password(ofNullable(update.getPassword()).orElse(found.getPassword()))
            .build();
    }
}
