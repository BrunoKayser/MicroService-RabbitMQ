package com.br.brunokayser.myfood.cadastro.service;

import com.br.brunokayser.myfood.cadastro.domain.Client;
import com.br.brunokayser.myfood.cadastro.domain.ClientOrderDto;
import com.br.brunokayser.myfood.cadastro.domain.LoginDto;
import com.br.brunokayser.myfood.cadastro.port.ClientRepository;
import com.br.brunokayser.myfood.cadastro.port.ClientSendMessage;
import com.br.brunokayser.myfood.cadastro.port.LoginSendMessage;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientSendMessage clientSendMessage;
    private final LoginSendMessage loginSendMessage;

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
