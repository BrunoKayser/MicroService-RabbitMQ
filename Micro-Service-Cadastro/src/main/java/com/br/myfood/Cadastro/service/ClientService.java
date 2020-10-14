package com.br.myfood.Cadastro.service;

import com.br.myfood.Cadastro.dto.ClientOrderDto;
import com.br.myfood.Cadastro.dto.LoginDto;
import com.br.myfood.Cadastro.entity.Client;
import com.br.myfood.Cadastro.message.ClientSendMessage;
import com.br.myfood.Cadastro.message.LoginSendMessage;
import com.br.myfood.Cadastro.repository.ClientRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientSendMessage clientSendMessage;
    private final LoginSendMessage loginSendMessage;

    @Autowired
    public ClientService(ClientRepository clientRepository, ClientSendMessage clientSendMessage, LoginSendMessage loginSendMessage) {
        this.clientRepository = clientRepository;
        this.clientSendMessage = clientSendMessage;
        this.loginSendMessage = loginSendMessage;
    }

    public Client insertClient(Client client) {
        final var newClient = clientRepository.save(client);
        clientSendMessage.sendMessage(new ClientOrderDto(newClient.getId()));
        loginSendMessage.sendMessage(new LoginDto(newClient.getEmail(), newClient.getPassword()));
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
