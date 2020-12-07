package com.br.brunokayser.myfood.cadastro.service;

import com.br.brunokayser.myfood.cadastro.domain.Client;
import com.br.brunokayser.myfood.cadastro.domain.ClientOrderDto;
import com.br.brunokayser.myfood.cadastro.domain.LoginDto;
import com.br.brunokayser.myfood.cadastro.exception.BadRequestException;
import com.br.brunokayser.myfood.cadastro.port.ClientRepositoryPort;
import com.br.brunokayser.myfood.cadastro.port.ClientSendMessage;
import com.br.brunokayser.myfood.cadastro.port.LoginSendMessage;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepositoryPort clientRepositoryPort;
    private final ClientSendMessage clientSendMessage;
    private final LoginSendMessage loginSendMessage;

    private static final String TAG = "CADASTRO - ";

    public Client insertClient(Client client) {

        //TODO: Falta implementar messages a partir do message sourcee

        verifyIfExistsByEmail(client.getEmail());

        final var newClient = clientRepositoryPort.save(client);
        clientSendMessage.sendMessage(new ClientOrderDto(newClient.getId()));
        loginSendMessage.sendMessage(new LoginDto(newClient.getEmail(), newClient.getPassword()));

        log.info(TAG + " Successful insert client: {}", newClient);
        return newClient;
    }

    public Client updateClient(Client client) {

        var newClient = Optional.of(clientRepositoryPort.findById(client.getId()));

        if (newClient.isPresent()) {
            return clientRepositoryPort.save(client);
        } else {
            return null;
        }
    }

    public boolean deleteClient(Long id) {

        var deleteClient = Optional.of(clientRepositoryPort.findById(id));

        if (deleteClient.isPresent()) {
            clientRepositoryPort.delete(deleteClient.get());
            return true;
        } else {
            return false;
        }
    }

    public Client findById(Long id) {
        return clientRepositoryPort.findById(id);
    }

    private void verifyIfExistsByEmail(String email){
        if(clientRepositoryPort.existsByEmail(email)){
            log.error(TAG + " This client already exists");
            throw new BadRequestException(TAG + "This client already exists");
        }
    }
}
