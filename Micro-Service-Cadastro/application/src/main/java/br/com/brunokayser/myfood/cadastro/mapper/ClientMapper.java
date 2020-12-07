package br.com.brunokayser.myfood.cadastro.mapper;


import br.com.brunokayser.myfood.cadastro.dto.ClientDto;
import com.br.brunokayser.myfood.cadastro.domain.Client;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    public static Client toDomain(ClientDto clientDto){
        return new ModelMapper().map(clientDto, Client.class);
    }

    public static Client toDomain(ClientDto clientDto, Long id){
        var client = new ModelMapper().map(clientDto, Client.class);
        client.setId(id);
        return client;
    }

    public static ClientDto toDto(Client client){
        return new ModelMapper().map(client, ClientDto.class);
    }

}
