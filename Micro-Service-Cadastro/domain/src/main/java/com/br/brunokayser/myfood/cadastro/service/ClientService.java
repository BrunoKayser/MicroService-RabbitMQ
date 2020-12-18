package com.br.brunokayser.myfood.cadastro.service;

import com.br.brunokayser.myfood.cadastro.domain.Client;
import com.br.brunokayser.myfood.cadastro.domain.ClientOrderDto;
import com.br.brunokayser.myfood.cadastro.domain.LoginDto;
import com.br.brunokayser.myfood.cadastro.exception.BadRequestException;
import com.br.brunokayser.myfood.cadastro.exception.NotFoundException;
import com.br.brunokayser.myfood.cadastro.port.ClientRepositoryPort;
import com.br.brunokayser.myfood.cadastro.port.ClientSendMessage;
import com.br.brunokayser.myfood.cadastro.port.LoginSendMessage;
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

        verifyIfExistsByEmail(client.getEmail());

        final var newClient = clientRepositoryPort.save(client);
        clientSendMessage.sendMessage(new ClientOrderDto(newClient.getId()));
        loginSendMessage.sendMessage(new LoginDto(newClient.getEmail(), newClient.getPassword()));

        log.info(TAG + " Successful insert client: {}", newClient);
        return newClient;
    }

    public Client updateClient(Client client) {

        var newClient = clientRepositoryPort.findById(client.getId());

        throwsExceptionIfNotFound(newClient.isEmpty());

        verifyIfExistsByEmail(client.getEmail());

        log.info(TAG + " Successful insert client: {}", newClient);
        return clientRepositoryPort.save(client);
    }

    public void deleteClient(Long id) {

        var deletedClient = clientRepositoryPort.findById(id);

        var condition = deletedClient.isEmpty();

        throwsExceptionIfNotFound(condition);

        log.info(TAG + " Successful delete for client: {}", deletedClient);
        clientRepositoryPort.delete(deletedClient.get());
    }

    public Client findById(Long id) {

        var client = clientRepositoryPort.findById(id);

        throwsExceptionIfNotFound(client.isEmpty());

        log.info(TAG + "Get Successful client: {}", client.get());
        return client.get();
    }

    private void verifyIfExistsByEmail(String email) {
        if (clientRepositoryPort.existsByEmail(email)) {
            log.error(TAG + " This client already exists");
            throw new BadRequestException("client.already.exists");
        }
    }

    private void throwsExceptionIfNotFound(Boolean condition) {
        if(condition) {
            log.error(TAG + "This client doesnt exists");
            throw new NotFoundException("client.not.found");
        }
    }
}
