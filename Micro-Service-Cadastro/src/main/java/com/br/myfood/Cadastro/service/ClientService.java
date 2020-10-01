package com.br.myfood.Cadastro.service;

import com.br.myfood.Cadastro.dto.ClientOrderDto;
import com.br.myfood.Cadastro.entity.Client;
import com.br.myfood.Cadastro.message.ClientSendMessage;
import com.br.myfood.Cadastro.repository.ClientRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientSendMessage clientSendMessage;

    @Autowired
    public ClientService(ClientRepository clientRepository, ClientSendMessage clientSendMessage) {
        this.clientRepository = clientRepository;
        this.clientSendMessage = clientSendMessage;
    }

    public Client insertClient(Client client) {
        final var newClient = clientRepository.save(client);
        clientSendMessage.sendMessage(new ClientOrderDto(newClient.getId()));
        return newClient;
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
