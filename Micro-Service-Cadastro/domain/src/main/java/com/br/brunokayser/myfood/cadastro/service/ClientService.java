package com.br.brunokayser.myfood.cadastro.service;

import br.com.brunokayser.myfood.cadastro.dto.ClientOrderDto;
import br.com.brunokayser.myfood.cadastro.dto.LoginDto;
import com.br.brunokayser.myfood.cadastro.message.LoginSendMessage;
import com.br.brunokayser.myfood.cadastro.entity.Client;
import com.br.brunokayser.myfood.cadastro.message.ClientSendMessage;
import com.br.brunokayser.myfood.cadastro.repository.ClientRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
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
