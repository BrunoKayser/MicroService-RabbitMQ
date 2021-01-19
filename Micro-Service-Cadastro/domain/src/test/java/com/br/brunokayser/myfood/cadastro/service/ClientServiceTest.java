package com.br.brunokayser.myfood.cadastro.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.br.brunokayser.myfood.cadastro.fixture.ClientFixture;
import com.br.brunokayser.myfood.cadastro.exception.BadRequestException;
import com.br.brunokayser.myfood.cadastro.port.ClientRepositoryPort;
import com.br.brunokayser.myfood.cadastro.port.ClientSendMessage;
import com.br.brunokayser.myfood.cadastro.port.LoginSendMessage;
import com.br.brunokayser.myfood.cadastro.validator.ServiceValidator;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @InjectMocks
    private ClientService clientService;

    @Mock
    private ServiceValidator serviceValidator;

    @Mock
    private ClientRepositoryPort clientRepositoryPort;

    @Mock
    private ClientSendMessage clientSendMessage;

    @Mock
    private LoginSendMessage loginSendMessage;

    private static final Long ID_MOCK = 1L;

    @Test
    void shouldInsertClientOk() {

        //Arrange
        var client = ClientFixture.getWithRandomData().withoutId().build();

        var newClient = ClientFixture
            .getWithRandomData()
            .withEmail(client.getEmail())
            .withId(ID_MOCK)
            .withName(client.getName())
            .withPassword(client.getPassword())
            .build();

        when(clientRepositoryPort.existsByEmail(client.getEmail())).thenReturn(Boolean.TRUE);
        when(clientRepositoryPort.save(client)).thenReturn(newClient);

        //Action
        var response = clientService.insertClient(client);

        //Assert
        verify(serviceValidator, times(1)).verifyIfExistsByEmail(Boolean.TRUE);
        verify(clientSendMessage, times(1)).sendMessage(any());
        verify(loginSendMessage, times(1)).sendMessage(any());
    }

    @Test
    public void shouldThrowsExceptionIfClientNotFound(){

        //Arrange
        var client = ClientFixture.getWithRandomData().withoutId().build();

        when(clientRepositoryPort.existsByEmail(client.getEmail())).thenReturn(Boolean.FALSE);

        doThrow(BadRequestException.class).when(serviceValidator).verifyIfExistsByEmail(Boolean.FALSE);

        //Action
        assertThrows(BadRequestException.class,
            () ->  clientService.insertClient(client));

        //Assert
        verify(serviceValidator, times(1)).verifyIfExistsByEmail(Boolean.FALSE);
        verify(clientSendMessage, never()).sendMessage(any());
        verify(loginSendMessage, never()).sendMessage(any());
    }

    @Test
    public void shouldUpdateClientOk(){

        //Arrange
        var client = ClientFixture.getWithRandomData().withoutId().build();

        when(clientRepositoryPort.findById(client.getId()))
            .thenReturn(Optional.of(client));

        when(clientRepositoryPort.existsByEmail(client.getEmail()))
            .thenReturn(Boolean.FALSE);

        //Action
        var response = clientService.updateClient(client);

        //Assert
        verify(serviceValidator,times(1)).validateIfFound(Boolean.FALSE);
        verify(serviceValidator,times(1)).verifyIfExistsByEmail(Boolean.FALSE);
        verify(clientRepositoryPort,times(1)).update(any());

        assertEquals(client.getPassword(), response.getPassword());
        assertEquals(client.getEmail(), response.getEmail());
        assertEquals(client.getName(), response.getName());
        assertEquals(client.getId(), response.getId());
    }

    @Test
    public void shouldDeleteClientOk(){

        //Arrange
        var client = ClientFixture.getWithRandomData().withId(ID_MOCK).build();

        when(clientRepositoryPort.findById(client.getId()))
            .thenReturn(Optional.of(client));

        //Action
        clientService.deleteClient(ID_MOCK);

        //Assert
        verify(clientRepositoryPort,times(1)).findById(client.getId());
        verify(serviceValidator,times(1)).validateIfFound(Boolean.FALSE);
        verify(clientRepositoryPort,times(1)).delete(client);
    }

    @Test
    public void shouldFindByIdOk(){

        //Arrange
        var client = ClientFixture.getWithRandomData().withId(ID_MOCK).build();

        when(clientRepositoryPort.findById(client.getId()))
            .thenReturn(Optional.of(client));

        //Action
        var response = clientService.findById(ID_MOCK);

        //Assert
        verify(clientRepositoryPort,times(1)).findById(client.getId());
        verify(serviceValidator,times(1)).validateIfFound(Boolean.FALSE);

        assertEquals(ID_MOCK, response.getId());
        assertEquals(client.getName(), response.getName());
        assertEquals(client.getEmail(), response.getEmail());
        assertEquals(client.getPassword(), response.getPassword());
    }
}
