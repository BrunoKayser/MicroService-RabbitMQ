package com.br.myfood.Cadastro.service;

import com.br.myfood.Cadastro.entity.Client;
import com.br.myfood.Cadastro.repository.ClientRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {


    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client insertClient(Client client) {
        return clientRepository.save(client);
    }

    public Client updateClient(Client client) {

        var newClient = clientRepository.findById(client.getId());

        if (newClient.isPresent()) {
            return clientRepository.save(client);
        } else {
            return null;
        }
    }

    public boolean deleteClient(Long id) {

        var deleteClient = clientRepository.findById(id);

        if (deleteClient.isPresent()) {
            clientRepository.delete(deleteClient.get());
            return true;
        } else {
            return false;
        }
    }

    public Optional<Client> findById(Long id) {

        return clientRepository.findById(id);

    }

}
