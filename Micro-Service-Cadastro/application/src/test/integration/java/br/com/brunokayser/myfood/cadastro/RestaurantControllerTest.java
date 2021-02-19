package br.com.brunokayser.myfood.cadastro;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.brunokayser.myfood.cadastro.fixture.RestaurantDtoFixture;
import br.com.brunokayser.myfood.cadastro.fixture.RestaurantFixture;
import com.br.brunokayser.myfood.cadastro.domain.Restaurant;
import com.br.brunokayser.myfood.cadastro.port.RestaurantRepositoryPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

public class RestaurantControllerTest extends BaseIntegrationTest {

    @MockBean
    private RestaurantRepositoryPort restaurantRepositoryPort;

    private static final Long ID_MOCK = 1L;

    @Test
    public void shouldInsertRestaurantOk() throws Exception {

        //Arrange
        var request = RestaurantFixture
            .getWithRandomData()
            .withoutId()
            .build();

        var restaurantSaved = request;
        restaurantSaved.setId(ID_MOCK);

        when(restaurantRepositoryPort.existsByEmailOrName(request.getEmail(), request.getName()))
            .thenReturn(Boolean.FALSE);

        when(restaurantRepositoryPort.save(restaurantSaved))
            .thenReturn(restaurantSaved);

        //Action /Assert
        mockMvc.perform(
            post("/restaurant/insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(restaurantSaved.getId()))
            .andExpect(jsonPath("$.name").value(request.getName()))
            .andExpect(jsonPath("$.email").value(request.getEmail()))
            .andExpect(jsonPath("$.password").value(request.getPassword()));


        verify(restaurantRepositoryPort, times(1)).existsByEmailOrName(request.getEmail(), request.getName());
        verify(restaurantRepositoryPort, times(1)).save(restaurantSaved);
    }

    @Test
    public void shouldNotInsertRestaurantWhenPasswordHasInvalidFormat() throws Exception {

        //Arrange
        var request = RestaurantFixture
            .getWithRandomData()
            .withoutId()
            .withPassword("12df")
            .build();

        //Action /Assert
        mockMvc.perform(
            post("/restaurant/insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.messageError").value("The password must have one word, one number and size more than 6"));


        verify(restaurantRepositoryPort, times(0)).existsByEmailOrName(request.getEmail(), request.getName());
        verify(restaurantRepositoryPort, times(0)).save(any(Restaurant.class));
    }

    @Test
    public void shouldNotInsertRestaurantWhenEmailHasInvalidFormat() throws Exception {

        //Arrange
        var request = RestaurantFixture
            .getWithRandomData()
            .withoutId()
            .withEmail("email.com.br")
            .build();

        //Action /Assert
        mockMvc.perform(
            post("/restaurant/insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.messageError").value("Invalid e-mail format"));


        verify(restaurantRepositoryPort, times(0)).existsByEmailOrName(request.getEmail(), request.getName());
        verify(restaurantRepositoryPort, times(0)).save(any(Restaurant.class));
    }

    @Test
    public void shouldNotInsertRestaurantWhenEmailOrNameAlreadyExists() throws Exception {

        //Arrange
        var request = RestaurantFixture
            .getWithRandomData()
            .withoutId()
            .build();

        when(restaurantRepositoryPort.existsByEmailOrName(request.getEmail(), request.getName()))
            .thenReturn(Boolean.TRUE);

        //Action /Assert
        mockMvc.perform(
            post("/restaurant/insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.messageError").value("CADASTRO - This restaurant name or email already exists"));


        verify(restaurantRepositoryPort, times(1)).existsByEmailOrName(request.getEmail(), request.getName());
        verify(restaurantRepositoryPort, times(0)).save(any(Restaurant.class));
    }

    @Test
    public void shouldUpdateRestaurantOk() throws Exception {

        //Arrange
        var request = RestaurantDtoFixture
            .getWithRandomData()
            .withoutId()
            .build();

        var restaurantFound =  RestaurantFixture
            .getWithRandomData()
            .withoutId()
            .build();

        when(restaurantRepositoryPort.findById(ID_MOCK))
            .thenReturn(Optional.of(restaurantFound));

        when(restaurantRepositoryPort.existsByEmailOrName(request.getEmail(), request.getName()))
            .thenReturn(Boolean.FALSE);

        //Action /Assert
        mockMvc.perform(
            put("/restaurant/update/{ID_MOCK}", ID_MOCK)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(ID_MOCK))
            .andExpect(jsonPath("$.name").value(request.getName()))
            .andExpect(jsonPath("$.email").value(request.getEmail()))
            .andExpect(jsonPath("$.password").value(request.getPassword()));


        verify(restaurantRepositoryPort, times(1)).existsByEmailOrName(request.getEmail(), request.getName());
        verify(restaurantRepositoryPort).update(any(Restaurant.class));
    }

    @Test
    public void shouldVerifyTheParametherToUpdateInRepository() throws Exception {

        //Arrange
        var request = RestaurantDtoFixture
            .getWithRandomData()
            .withoutId()
            .build();

        var restaurantFound =  RestaurantFixture
            .getWithRandomData()
            .withoutId()
            .build();

        when(restaurantRepositoryPort.findById(ID_MOCK))
            .thenReturn(Optional.of(restaurantFound));

        when(restaurantRepositoryPort.existsByEmailOrName(request.getEmail(), request.getName()))
            .thenReturn(Boolean.FALSE);

        //Action /Assert
        mockMvc.perform(
            put("/restaurant/update/{ID_MOCK}", ID_MOCK)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isOk());


        var restaurantCaptur = ArgumentCaptor.forClass(Restaurant.class);

        verify(restaurantRepositoryPort, times(1)).existsByEmailOrName(request.getEmail(), request.getName());
        verify(restaurantRepositoryPort).update(restaurantCaptur.capture());

        assertEquals(request.getEmail(), restaurantCaptur.getValue().getEmail());
        assertEquals(ID_MOCK, restaurantCaptur.getValue().getId());
        assertEquals(request.getName(), restaurantCaptur.getValue().getName());
        assertEquals(request.getPassword(), restaurantCaptur.getValue().getPassword());
    }

    @Test
    public void shouldNotUpdateRestaurantWhenHasInvalidPassword() throws Exception {

        //Arrange
        var request = RestaurantDtoFixture
            .getWithRandomData()
            .withoutId()
            .withPassword("12316541984")
            .build();

        //Action /Assert
        mockMvc.perform(
            put("/restaurant/update/{ID_MOCK}", ID_MOCK)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.messageError").value("The password must have one word, one number and size more than 6"));


        verify(restaurantRepositoryPort, times(0)).existsByEmailOrName(request.getEmail(), request.getName());
        verify(restaurantRepositoryPort, times(0)).update(any(Restaurant.class));
    }

    @Test
    public void shouldNotUpdateRestaurantWhenHasInvalidEmail() throws Exception {

        //Arrange
        var request = RestaurantDtoFixture
            .getWithRandomData()
            .withoutId()
            .withEmail("email.email.com.br")
            .build();

        //Action /Assert
        mockMvc.perform(
            put("/restaurant/update/{ID_MOCK}", ID_MOCK)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.messageError").value("Invalid e-mail format"));


        verify(restaurantRepositoryPort, times(0)).existsByEmailOrName(request.getEmail(), request.getName());
        verify(restaurantRepositoryPort, times(0)).update(any(Restaurant.class));
    }

    @Test
    public void shouldDeleteRestaurantOk() throws Exception {

        //Arrange
        var restaurant = RestaurantFixture
            .getWithRandomData()
            .withId(ID_MOCK)
            .build();

        when(restaurantRepositoryPort.findById(ID_MOCK))
            .thenReturn(Optional.of(restaurant));

        //Action /Assert
        mockMvc.perform(
            delete("/restaurant/delete/{ID_MOCK}", ID_MOCK)
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());


        verify(restaurantRepositoryPort, times(1)).findById(ID_MOCK);
        verify(restaurantRepositoryPort, times(1)).delete(restaurant);
    }

    @Test
    public void shouldThrowsNotFoundExpeptionWhenNotFoundRestaurantToDelete() throws Exception {

        //Arrange
        when(restaurantRepositoryPort.findById(ID_MOCK))
            .thenReturn(Optional.empty());

        //Action /Assert
        mockMvc.perform(
            delete("/restaurant/delete/{ID_MOCK}", ID_MOCK)
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.messageError").value("CADASTRO - Id not found"));


        verify(restaurantRepositoryPort, times(1)).findById(ID_MOCK);
        verify(restaurantRepositoryPort, times(0)).delete(any());
    }

    @Test
    public void shouldFindRestaurantOk() throws Exception {

        //Arrange
        var restaurant = RestaurantFixture
            .getWithRandomData()
            .withId(ID_MOCK)
            .build();

        when(restaurantRepositoryPort.findById(ID_MOCK))
            .thenReturn(Optional.of(restaurant));

        //Action /Assert
        mockMvc.perform(
            get("/restaurant/find/{ID_MOCK}", ID_MOCK)
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(ID_MOCK))
            .andExpect(jsonPath("$.name").value(restaurant.getName()))
            .andExpect(jsonPath("$.email").value(restaurant.getEmail()))
            .andExpect(jsonPath("$.password").value(restaurant.getPassword()));

        verify(restaurantRepositoryPort, times(1)).findById(ID_MOCK);
    }

    @Test
    public void shouldThrowsNotFoundExceptionWhenIdDoesNotExistsInFindRestaurant() throws Exception {

        //Arrange
        when(restaurantRepositoryPort.findById(ID_MOCK))
            .thenReturn(Optional.empty());

        //Action /Assert
        mockMvc.perform(
            get("/restaurant/find/{ID_MOCK}", ID_MOCK)
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.messageError").value("CADASTRO - Id not found"));

        verify(restaurantRepositoryPort, times(1)).findById(ID_MOCK);
    }
}
