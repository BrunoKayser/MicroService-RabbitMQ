package com.br.myfood.Cadastro.service;

import com.br.myfood.Cadastro.entity.Client;
import com.br.myfood.Cadastro.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {


    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository ;
    }

    public Client insertClient(Client client){
        return clientRepository.save(client);
    }


}
