package com.br.myfood.Cadastro.mapper;

import com.br.myfood.Cadastro.dto.ClientDto;
import com.br.myfood.Cadastro.entity.Client;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    public static Client toEntity(ClientDto clientDto){
        return new ModelMapper().map(clientDto, Client.class);
    }

}
