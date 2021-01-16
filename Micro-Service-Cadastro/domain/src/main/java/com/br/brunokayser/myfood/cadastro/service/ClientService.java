package com.br.brunokayser.myfood.cadastro.service;

import static com.br.brunokayser.myfood.cadastro.domain.enums.Constant.TAG;
import static com.br.brunokayser.myfood.cadastro.mapper.ClientMapper.buildClientToUpdate;

import com.br.brunokayser.myfood.cadastro.domain.Client;
import com.br.brunokayser.myfood.cadastro.domain.ClientOrderDto;
import com.br.brunokayser.myfood.cadastro.domain.LoginDto;
import com.br.brunokayser.myfood.cadastro.port.ClientRepositoryPort;
import com.br.brunokayser.myfood.cadastro.port.ClientSendMessage;
import com.br.brunokayser.myfood.cadastro.port.LoginSendMessage;
import com.br.brunokayser.myfood.cadastro.validator.ServiceValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepositoryPort clientRepositoryPort;
    private final ClientSendMessage clientSendMessage;
    private final LoginSendMessage loginSendMessage;
    private final ServiceValidator serviceValidator;

    public Client insertClient(Client client) {

        serviceValidator.verifyIfExistsByEmail(clientRepositoryPort.existsByEmail(client.getEmail()));

        final var newClient = clientRepositoryPort.save(client);
        clientSendMessage.sendMessage(new ClientOrderDto(newClient.getId()));
        loginSendMessage.sendMessage(new LoginDto(newClient.getEmail(), newClient.getPassword()));

        log.info(TAG + " Successful insert client: {}", newClient);
        return newClient;
    }

    public Client updateClient(Client client) {

        var clientSearched = clientRepositoryPort.findById(client.getId());

        serviceValidator.validateIfFound(clientSearched.isEmpty());
        serviceValidator.verifyIfExistsByEmail(clientRepositoryPort.existsByEmail(client.getEmail()));

        clientRepositoryPort.update(buildClientToUpdate(client, clientSearched.get()));

        log.info(TAG + " Successful update client: {}", client);
        return client;
    }

    public void deleteClient(Long id) {

        var deletedClient = clientRepositoryPort.findById(id);

        serviceValidator.validateIfFound(deletedClient.isEmpty());

        clientRepositoryPort.delete(deletedClient.get());

        log.info(TAG + " Successful delete for client: {}", deletedClient);
    }

    public Client findById(Long id) {

        var client = clientRepositoryPort.findById(id);

        serviceValidator.validateIfFound(client.isEmpty());

        log.info(TAG + "Get Successful client: {}", client.get());
        return client.get();
    }
}
