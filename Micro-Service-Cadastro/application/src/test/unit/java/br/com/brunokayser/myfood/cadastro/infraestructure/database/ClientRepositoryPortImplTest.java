package br.com.brunokayser.myfood.cadastro.infraestructure.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.brunokayser.myfood.cadastro.dto.ClientDto;
import br.com.brunokayser.myfood.cadastro.fixture.ClientDtoFixture;
import br.com.brunokayser.myfood.cadastro.fixture.ClientFixture;
import br.com.brunokayser.myfood.cadastro.infraestructure.database.persistence.ClientRepository;
import com.br.brunokayser.myfood.cadastro.exception.InternalErrorException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ClientRepositoryPortImplTest {

    @InjectMocks
    private ClientRepositoryPortImpl clientRepositoryPort;

    @Mock
    private ClientRepository clientRepository;

    private static final Long ID_MOCK = 1L;
    private static final String EMAIL_MOCK = "BrunoKayser@gmail.com";

    @Test
    public void shouldSaveOk() {

        //Arrange
        var client = ClientFixture
            .getWithRandomData()
            .withoutId()
            .build();

        var clientDtoReturned = ClientDtoFixture
            .getWithRandomData()
            .withEmail(client.getEmail())
            .withName(client.getName())
            .withPassword(client.getPassword())
            .withId(ID_MOCK)
            .build();

        when(clientRepository.save(any(ClientDto.class)))
            .thenReturn(clientDtoReturned);

        //Action
        var response = clientRepositoryPort.save(client);

        //Assert
        verify(clientRepository, times(1)).save(any(ClientDto.class));

        assertEquals(client.getEmail(), response.getEmail());
        assertEquals(client.getName(), response.getName());
        assertEquals(client.getPassword(), response.getPassword());
        assertEquals(ID_MOCK, response.getId());
    }

    @Test
    public void shouldThrowsInternalServerErrorWhenHasProblemToSave() {

        //Arrange
        var client = ClientFixture
            .getWithRandomData()
            .withoutId()
            .build();

        when(clientRepository.save(any()))
            .thenThrow(new InternalErrorException("error.save.client.repository"));

        //Action
        var exceptionResponse = assertThrows(InternalErrorException.class,
            () -> clientRepositoryPort.save(client));

        //Assert
        verify(clientRepository).save(any());

        assertEquals("error.save.client.repository", exceptionResponse.getMessage());
    }

    @Test
    public void shouldFindByIdOK() {

        //Arrange
        var client = ClientDtoFixture
            .getWithRandomData()
            .withId(ID_MOCK)
            .build();

        when(clientRepository.findById(ID_MOCK))
            .thenReturn(Optional.of(client));

        //Action
        var response = clientRepositoryPort.findById(ID_MOCK);

        //Assert
        verify(clientRepository, times(1)).findById(ID_MOCK);

        assertEquals(ID_MOCK, response.get().getId());
        assertEquals(client.getEmail(), response.get().getEmail());
        assertEquals(client.getPassword(), response.get().getPassword());
        assertEquals(client.getName(), response.get().getName());
    }

    @Test
    public void shouldThrowsExceptionWhenHasProblemToFindById() {

        //Arrange
        when(clientRepository.findById(ID_MOCK))
            .thenThrow(new InternalErrorException("error.find.client.repository"));

        //Action
        var exceptionResponse = assertThrows(InternalErrorException.class,
            () -> clientRepositoryPort.findById(ID_MOCK));

        //Assert
        verify(clientRepository, times(1)).findById(ID_MOCK);

        assertEquals("error.find.client.repository", exceptionResponse.getMessage());
    }

    @Test
    public void shouldDeleteOK() {

        //Arrange
        var client = ClientFixture
            .getWithRandomData()
            .withId(ID_MOCK)
            .build();

        //Action
        clientRepositoryPort.delete(client);

        //Assert
        var clientDtoCaptur = ArgumentCaptor.forClass(ClientDto.class);
        verify(clientRepository, times(1)).delete(clientDtoCaptur.capture());

        assertEquals(client.getEmail(), clientDtoCaptur.getValue().getEmail());
        assertEquals(client.getId(), clientDtoCaptur.getValue().getId());
        assertEquals(client.getName(), clientDtoCaptur.getValue().getName());
        assertEquals(client.getPassword(), clientDtoCaptur.getValue().getPassword());
    }

    @Test
    public void shouldThrowsExceptionWhenHasProblemToDeleteClient() {

        //Arrange
        var client = ClientFixture
            .getWithRandomData()
            .withId(ID_MOCK)
            .build();

        doThrow(InternalErrorException.class)
            .when(clientRepository)
            .delete(any());

        //Action
        var exceptionResponse = assertThrows(InternalErrorException.class,
            () -> clientRepositoryPort.delete(client));

        //Assert
        verify(clientRepository, times(1)).delete(any());

        assertEquals("error.delete.client.repository", exceptionResponse.getMessage());
    }

    @Test
    public void shouldTestExistsByEmailTrue() {

        //Arrange
        when(clientRepository.existsByEmail(EMAIL_MOCK))
            .thenReturn(Boolean.TRUE);

        //Action
        var booleanResul = clientRepositoryPort.existsByEmail(EMAIL_MOCK);

        //Assert
        verify(clientRepository, times(1)).existsByEmail(EMAIL_MOCK);

        assertTrue(booleanResul);
    }

    @Test
    public void shouldTestExistsByEmailFalse() {

        //Arrange
        when(clientRepository.existsByEmail(EMAIL_MOCK))
            .thenReturn(Boolean.FALSE);

        //Action
        var booleanResul = clientRepositoryPort.existsByEmail(EMAIL_MOCK);

        //Assert
        verify(clientRepository, times(1)).existsByEmail(EMAIL_MOCK);

        assertFalse(booleanResul);
    }

    @Test
    public void shouldThrowsExceptionWhenHasProblemToExistsByEmailMethod() {

        //Arrange
        when(clientRepository.existsByEmail(EMAIL_MOCK))
            .thenThrow(new InternalErrorException("aaaa"));

        //Action
        var errorException = assertThrows(InternalErrorException.class,
            ()-> clientRepositoryPort.existsByEmail(EMAIL_MOCK));

        //Assert
        verify(clientRepository, times(1)).existsByEmail(EMAIL_MOCK);

        assertEquals("error.existBy.client.repository", errorException.getMessage());
    }

    @Test
    public void shouldUpdateOk() {

        //Arrange
        var client = ClientFixture
            .getWithRandomData()
            .build();

        //Action
        clientRepositoryPort.update(client);

        //Assert
        verify(clientRepository).update(client.getEmail(), client.getName(), client.getPassword(), client.getId());
    }


    @Test
    public void shouldThrowsExceptionWhenHasProblemToUpdateClient() {

        //Arrange
        var client = ClientFixture
            .getWithRandomData()
            .build();

        doThrow(InternalErrorException.class)
            .when(clientRepository)
            .update(client.getEmail(), client.getName(), client.getPassword(), client.getId());


        //Action
        var exceptionResponse = assertThrows(InternalErrorException.class,
            ()-> clientRepositoryPort.update(client));

        //Assert
        verify(clientRepository).update(client.getEmail(), client.getName(), client.getPassword(), client.getId());

        assertEquals("error.update.client.repository", exceptionResponse.getMessage());
    }
}
