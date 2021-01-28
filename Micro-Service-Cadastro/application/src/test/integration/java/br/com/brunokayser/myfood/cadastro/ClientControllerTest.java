package br.com.brunokayser.myfood.cadastro;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.brunokayser.myfood.cadastro.dto.ClientDto;
import com.br.brunokayser.myfood.cadastro.domain.Client;
import com.br.brunokayser.myfood.cadastro.domain.ClientOrderDto;
import com.br.brunokayser.myfood.cadastro.domain.LoginDto;
import com.br.brunokayser.myfood.cadastro.exception.BadRequestException;
import com.br.brunokayser.myfood.cadastro.port.ClientRepositoryPort;
import com.br.brunokayser.myfood.cadastro.port.ClientSendMessage;
import com.br.brunokayser.myfood.cadastro.port.LoginSendMessage;
import com.br.brunokayser.myfood.cadastro.validator.ServiceValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import fixture.ClientDtoFixture;
import fixture.ClientFixture;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;


public class ClientControllerTest extends BaseIntegrationTest {

    @Mock
    private ServiceValidator serviceValidator;

    @MockBean
    private ClientRepositoryPort clientRepositoryPort;

    @MockBean
    private ClientSendMessage clientSendMessage;

    @MockBean
    private LoginSendMessage loginSendMessage;

    private static final Long ID_MOCK = 1L;
    private static final String EMAIL_WRONG_MOCK = "mail.mail.com";
    private static final String PASSWORD_WRONG_MOCK = "123123123";
    private static final String PASSWORD_SIZE_WRONG_MOCK = "123Aa";

    @Test
    public void shouldInsertClientOk() throws Exception {

        //Arrange
        var request = ClientDtoFixture.getWithRandomData().withoutId().build();

        var clientSaved = ClientFixture
            .getWithRandomData()
            .withEmail(request.getEmail())
            .withName(request.getName())
            .withPassword(request.getPassword())
            .withId(ID_MOCK)
            .build();

        when(clientRepositoryPort.existsByEmail(request.getEmail()))
            .thenReturn(Boolean.FALSE);

        when(clientRepositoryPort.save(any(Client.class)))
            .thenReturn(clientSaved);

        //Action
        mockMvc.perform(
            post("/client/insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
            .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(clientSaved.getId()))
        .andExpect(jsonPath("$.name").value(clientSaved.getName()))
        .andExpect(jsonPath("$.email").value(clientSaved.getEmail()))
        .andExpect(jsonPath("$.password").value(clientSaved.getPassword()));

        //Assert
        verify(clientRepositoryPort, times(1)).existsByEmail(request.getEmail());
        verify(clientRepositoryPort, times(1)).save(any(Client.class));
        verify(clientSendMessage, times(1)).sendMessage(any(ClientOrderDto.class));
        verify(loginSendMessage, times(1)).sendMessage(any(LoginDto.class));

    }

    @Test
    public void shouldNotInsertClientWithWrongEmail() throws Exception {

        //Arrange
        var request = ClientDtoFixture
            .getWithRandomData()
            .withoutId()
            .withEmail(EMAIL_WRONG_MOCK)
            .build();

        //Action / Assert
        mockMvc.perform(
            post("/client/insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.messageError").value("Invalid e-mail format"));
    }

    @Test
    public void shouldNotInsertClientWithWrongPassword() throws Exception {

        //Arrange / Assert
        var request = ClientDtoFixture
            .getWithRandomData()
            .withoutId()
            .withPassword(PASSWORD_WRONG_MOCK)
            .build();

        //Action
        mockMvc.perform(
            post("/client/insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.messageError").value("The password must have one word, one number and size more than 6"));
    }

    @Test
    public void shouldNotInsertClientWithoutRequest() throws Exception {

        ClientDto request = null;

        //Arrange /Action / Assert
        mockMvc.perform(
            post("/client/insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldNotInsertClientWhenEmailAlreadyExists() throws Exception {

        //Arrange
        var request = ClientDtoFixture.getWithRandomData().withoutId().build();

        var clientSaved = ClientFixture
            .getWithRandomData()
            .withEmail(request.getEmail())
            .withName(request.getName())
            .withPassword(request.getPassword())
            .withId(ID_MOCK)
            .build();

        when(clientRepositoryPort.existsByEmail(request.getEmail()))
            .thenReturn(Boolean.TRUE);

        doThrow(BadRequestException.class)
            .when(serviceValidator)
            .verifyIfExistsByEmail(Boolean.TRUE);

        //Action
        mockMvc.perform(
            post("/client/insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.messageError").value("CADASTRO - This e-mail already exists"));

        //Assert
        verify(clientRepositoryPort, times(1)).existsByEmail(request.getEmail());
        verify(clientRepositoryPort, never()).save(any(Client.class));
        verify(clientSendMessage, never()).sendMessage(any(ClientOrderDto.class));
        verify(loginSendMessage, never()).sendMessage(any(LoginDto.class));

    }

    @Test
    public void shouldUpdateClientOk() throws Exception {

        //Arrange
        var request = ClientDtoFixture
            .getWithRandomData()
            .withId(ID_MOCK)
            .build();

        var dataToUpdate = ClientFixture
            .getWithRandomData()
            .withEmail(request.getEmail())
            .withName(request.getName())
            .withPassword(request.getPassword())
            .build();

        var clientFound = ClientFixture
            .getWithRandomData()
            .withId(ID_MOCK)
            .build();

        when(clientRepositoryPort.findById(ID_MOCK))
            .thenReturn(Optional.of(clientFound));

        when(clientRepositoryPort.existsByEmail(dataToUpdate.getEmail()))
            .thenReturn(Boolean.FALSE);


        //Action
        mockMvc.perform(
            put("/client/update/{ID_MOCK}", ID_MOCK)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(request.getId()))
            .andExpect(jsonPath("$.name").value(request.getName()))
            .andExpect(jsonPath("$.email").value(request.getEmail()))
            .andExpect(jsonPath("$.password").value(request.getPassword()));

        //Assert
        verify(clientRepositoryPort, times(1)).findById(ID_MOCK);
        verify(clientRepositoryPort, times(1)).existsByEmail(dataToUpdate.getEmail());
        verify(clientRepositoryPort, times(1)).update(any(Client.class));
    }

    @Test
    public void shouldNotUpdateClientWithWrongEmail() throws Exception {

        //Arrange
        var request = ClientDtoFixture
            .getWithRandomData()
            .withId(ID_MOCK)
            .withEmail(EMAIL_WRONG_MOCK)
            .build();

        //Action /Assert
        mockMvc.perform(
            put("/client/update/{ID_MOCK}", ID_MOCK)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.messageError").value("Invalid e-mail format"));
    }

    @Test
    public void shouldNotUpdateClientWithWrongPassword() throws Exception {

        //Arrange
        var request = ClientDtoFixture
            .getWithRandomData()
            .withId(ID_MOCK)
            .withPassword(PASSWORD_SIZE_WRONG_MOCK)
            .build();

        //Action /Assert
        mockMvc.perform(
            put("/client/update/{ID_MOCK}", ID_MOCK)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.messageError").value("The password must have one word, one number and size more than 6"));
    }

    @Test
    public void shouldNotUpdateClientWhenClientDoenstExist() throws Exception {

        //Arrange
        var request = ClientDtoFixture
            .getWithRandomData()
            .withId(ID_MOCK)
            .build();

        var dataToUpdate = ClientFixture
            .getWithRandomData()
            .withEmail(request.getEmail())
            .withName(request.getName())
            .withPassword(request.getPassword())
            .build();

        when(clientRepositoryPort.findById(ID_MOCK))
            .thenReturn(Optional.empty());

        //Action
        mockMvc.perform(
            put("/client/update/{ID_MOCK}", ID_MOCK)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.messageError").value("CADASTRO - Id not found"));

        //Assert
        verify(clientRepositoryPort, times(1)).findById(ID_MOCK);
        verify(clientRepositoryPort, never()).existsByEmail(dataToUpdate.getEmail());
        verify(clientRepositoryPort, never()).update(any(Client.class));
    }

    @Test
    public void shouldNotUpdateClientWhenEmailAlreadyExists() throws Exception {

        //Arrange
        var request = ClientDtoFixture.getWithRandomData().build();

        var clientFound = ClientFixture.getWithRandomData().build();

        when(clientRepositoryPort.findById(ID_MOCK))
            .thenReturn(Optional.of(clientFound));

        when(clientRepositoryPort.existsByEmail(request.getEmail()))
            .thenReturn(Boolean.TRUE);

        //Action
        mockMvc.perform(
            put("/client/update/{ID_MOCK}", ID_MOCK)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.messageError").value("CADASTRO - This e-mail already exists"));

        //Assert
        verify(clientRepositoryPort, times(1)).findById(ID_MOCK);
        verify(clientRepositoryPort,times(1)).existsByEmail(request.getEmail());
        verify(clientRepositoryPort, never()).update(any(Client.class));
    }

}
