package br.com.brunokayser.myfood.cadastro;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
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

import br.com.brunokayser.myfood.cadastro.fixture.MenuFixture;
import br.com.brunokayser.myfood.cadastro.fixture.MenuInsertDtoFixture;
import br.com.brunokayser.myfood.cadastro.fixture.RestaurantFixture;
import com.br.brunokayser.myfood.cadastro.domain.Menu;
import com.br.brunokayser.myfood.cadastro.port.MenuRepositoryPort;
import com.br.brunokayser.myfood.cadastro.port.MenuSendMessage;
import com.br.brunokayser.myfood.cadastro.port.RestaurantRepositoryPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

public class MenuControllerTest extends BaseIntegrationTest {

    @MockBean
    private MenuRepositoryPort menuRepositoryPort;

    @MockBean
    private RestaurantRepositoryPort restaurantRepositoryPort;

    @MockBean
    private MenuSendMessage menuSendMessage;

    private static final Long ID_MENU_MOCK = 1L;
    private static final Long ID_RESTAURANT_MOCK = 2L;

    @Test
    public void shouldInsertMenuOk() throws Exception {

        //Arrange
        var request = MenuInsertDtoFixture.getWithRandomData().build();

        var restaurant = RestaurantFixture
            .getWithRandomData()
            .withId(request.getRestaurant())
            .build();

        var menu = MenuFixture.getWithRandomData().build();

        when(restaurantRepositoryPort.findById(request.getRestaurant()))
            .thenReturn(Optional.of(restaurant));

        when(menuRepositoryPort.existsByName(request.getName()))
            .thenReturn(Boolean.FALSE);

        when(restaurantRepositoryPort.existsById(request.getRestaurant()))
            .thenReturn(Boolean.TRUE);

        when(menuRepositoryPort.save(any(Menu.class)))
            .thenReturn(menu);

        //Action /Assert
        mockMvc.perform(
            post("/menu/insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(menu.getId()))
            .andExpect(jsonPath("$.name").value(menu.getName()))
            .andExpect(jsonPath("$.price").value(menu.getPrice()))
            .andExpect(jsonPath("$.restaurant").value(menu.getRestaurant()));

        verify(restaurantRepositoryPort, times(1)).findById(request.getRestaurant());
        verify(menuRepositoryPort, times(1)).existsByName(request.getName());
        verify(restaurantRepositoryPort, times(1)).existsById(request.getRestaurant());
        verify(menuRepositoryPort, times(1)).save(any(Menu.class));
    }

    @Test
    public void shouldVerifyWhatsIsTheMenuToBeSave() throws Exception {

        //Arrange
        var request = MenuInsertDtoFixture.getWithRandomData().build();

        var restaurant = RestaurantFixture
            .getWithRandomData()
            .withId(request.getRestaurant())
            .build();

        var menu = MenuFixture
            .getWithRandomData()
            .withName(request.getName())
            .withRestaurant(restaurant)
            .build();

        when(restaurantRepositoryPort.findById(request.getRestaurant()))
            .thenReturn(Optional.of(restaurant));

        when(menuRepositoryPort.existsByName(request.getName()))
            .thenReturn(Boolean.FALSE);

        when(restaurantRepositoryPort.existsById(request.getRestaurant()))
            .thenReturn(Boolean.TRUE);

        when(menuRepositoryPort.save(any(Menu.class)))
            .thenReturn(menu);

        //Action
        mockMvc.perform(
            post("/menu/insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isCreated());
        //Assert
        var menuCaptur = ArgumentCaptor.forClass(Menu.class);

        verify(restaurantRepositoryPort, times(1)).findById(request.getRestaurant());
        verify(menuRepositoryPort, times(1)).existsByName(request.getName());
        verify(restaurantRepositoryPort, times(1)).existsById(request.getRestaurant());

        verify(menuRepositoryPort, times(1)).save(menuCaptur.capture());

        assertNull(menuCaptur.getValue().getId());
        assertEquals(request.getPrice(), menuCaptur.getValue().getPrice());
        assertEquals(menu.getName(), menuCaptur.getValue().getName());
        assertEquals(menu.getRestaurant(), menuCaptur.getValue().getRestaurant());
    }

    @Test
    public void shouldNotInsertMenuWhenPriceIsNegative() throws Exception {

        //Arrange
        var request = MenuInsertDtoFixture
            .getWithRandomData()
            .withPrice(-2d)
            .build();

        //Action /Assert
        mockMvc.perform(
            post("/menu/insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.messageError").value("CADASTRO - The price can't be negative"));
    }

    @Test
    public void shouldThrowsNotFoundExceptionWhenInsertMenuHaveAInexistentRestaurant() throws Exception {

        //Arrange
        var request = MenuInsertDtoFixture.getWithRandomData().build();

        when(restaurantRepositoryPort.findById(request.getRestaurant()))
            .thenReturn(Optional.empty());

        //Action /Assert
        mockMvc.perform(
            post("/menu/insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.messageError").value("CADASTRO - Id not found"));

        verify(restaurantRepositoryPort, times(1)).findById(request.getRestaurant());
        verify(menuRepositoryPort, never()).existsByName(request.getName());
        verify(restaurantRepositoryPort, never()).existsById(request.getRestaurant());
        verify(menuRepositoryPort, never()).save(any(Menu.class));
    }

    @Test
    public void shouldThrowsBadRequestExceptionWhenFoodPlateOfInsertMenuAlreadyExists() throws Exception {

        //Arrange
        var request = MenuInsertDtoFixture.getWithRandomData().build();

        var restaurant = RestaurantFixture
            .getWithRandomData()
            .withId(request.getRestaurant())
            .build();

        var menu = MenuFixture.getWithRandomData().build();

        when(restaurantRepositoryPort.findById(request.getRestaurant()))
            .thenReturn(Optional.of(restaurant));

        when(menuRepositoryPort.existsByName(request.getName()))
            .thenReturn(Boolean.TRUE);

        when(restaurantRepositoryPort.existsById(request.getRestaurant()))
            .thenReturn(Boolean.TRUE);

        //Action /Assert
        mockMvc.perform(
            post("/menu/insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.messageError").value("CADASTRO - This plate plate of food already exists"));

        verify(restaurantRepositoryPort, times(1)).findById(request.getRestaurant());
        verify(menuRepositoryPort, times(1)).existsByName(request.getName());
        verify(restaurantRepositoryPort, times(1)).existsById(request.getRestaurant());
        verify(menuRepositoryPort, never()).save(any(Menu.class));
    }

    @Test
    public void shouldUpdateMenuOk() throws Exception {

        //Arrange
        var request = MenuInsertDtoFixture.getWithRandomData().build();

        var menu = MenuFixture
            .getWithRandomData()
            .withId(ID_MENU_MOCK)
            .build();

        var restaurant = Optional.of(RestaurantFixture
            .getWithRandomData()
            .withId(request.getRestaurant())
            .build());

        when(menuRepositoryPort.findById(ID_MENU_MOCK))
            .thenReturn(Optional.of(menu));

        when(restaurantRepositoryPort.findById(request.getRestaurant()))
            .thenReturn(restaurant);

        when(menuRepositoryPort.existsByName(request.getName()))
            .thenReturn(Boolean.FALSE);

        when(restaurantRepositoryPort.existsById(restaurant.get().getId()))
            .thenReturn(Boolean.FALSE);

        //Action /Assert
        mockMvc.perform(
            put("/menu/update/{ID_MENU_MOCK)}", ID_MENU_MOCK)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(ID_MENU_MOCK))
            .andExpect(jsonPath("$.name").value(request.getName()))
            .andExpect(jsonPath("$.price").value(request.getPrice()))
            .andExpect(jsonPath("$.restaurant").value(restaurant.get()));

        verify(menuRepositoryPort, times(1)).findById(ID_MENU_MOCK);
        verify(restaurantRepositoryPort, times(1)).findById(request.getRestaurant());
        verify(menuRepositoryPort, times(1)).existsByName(request.getName());
        verify(restaurantRepositoryPort, times(1)).existsById(restaurant.get().getId());
        verify(menuRepositoryPort, times(1)).update(any(Menu.class));
    }

    @Test
    public void shouldVerifyWhatIsTheMenuToBeUpdate() throws Exception {

        //Arrange
        var request = MenuInsertDtoFixture.getWithRandomData().build();

        var menu = MenuFixture
            .getWithRandomData()
            .withId(ID_MENU_MOCK)
            .build();

        var restaurant = Optional.of(RestaurantFixture
            .getWithRandomData()
            .withId(request.getRestaurant())
            .build());

        when(menuRepositoryPort.findById(ID_MENU_MOCK))
            .thenReturn(Optional.of(menu));

        when(restaurantRepositoryPort.findById(request.getRestaurant()))
            .thenReturn(restaurant);

        when(menuRepositoryPort.existsByName(request.getName()))
            .thenReturn(Boolean.FALSE);

        when(restaurantRepositoryPort.existsById(restaurant.get().getId()))
            .thenReturn(Boolean.FALSE);

        //Action
        mockMvc.perform(
            put("/menu/update/{ID_MENU_MOCK)}", ID_MENU_MOCK)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
            .andDo(print());

        //Assert
        var menuCaptur = ArgumentCaptor.forClass(Menu.class);
        verify(menuRepositoryPort, times(1)).findById(ID_MENU_MOCK);
        verify(restaurantRepositoryPort, times(1)).findById(request.getRestaurant());
        verify(menuRepositoryPort, times(1)).existsByName(request.getName());
        verify(restaurantRepositoryPort, times(1)).existsById(restaurant.get().getId());

        verify(menuRepositoryPort, times(1)).update(menuCaptur.capture());

        assertEquals(request.getName(), menuCaptur.getValue().getName());
        assertEquals(request.getPrice(), menuCaptur.getValue().getPrice());
        assertEquals(ID_MENU_MOCK, menuCaptur.getValue().getId());
        assertEquals(restaurant.get(), menuCaptur.getValue().getRestaurant());
    }

    @Test
    public void shouldThrowsBadRequestExceptionIfPriceofUpdateMenuIsNegative() throws Exception {

        //Arrange
        var request = MenuInsertDtoFixture
            .getWithRandomData()
            .withPrice(-2D)
            .build();

        //Action /Assert
        mockMvc.perform(
            put("/menu/update/{ID_MENU_MOCK)}", ID_MENU_MOCK)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.messageError").value("CADASTRO - The price can't be negative"));
    }

    @Test
    public void shouldThrowsNotFoundExceptionWhenIdMenuFromRequestOfMenuUpdateIsInexistent() throws Exception {

        //Arrange
        var request = MenuInsertDtoFixture.getWithRandomData().build();

        var menu = MenuFixture
            .getWithRandomData()
            .withId(ID_MENU_MOCK)
            .build();

        var restaurant = Optional.of(RestaurantFixture
            .getWithRandomData()
            .withId(request.getRestaurant())
            .build());

        when(menuRepositoryPort.findById(ID_MENU_MOCK))
            .thenReturn(Optional.empty());

        when(restaurantRepositoryPort.findById(anyLong()))
            .thenReturn(restaurant);

        //Action /Assert
        mockMvc.perform(
            put("/menu/update/{ID_MENU_MOCK)}", ID_MENU_MOCK)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isNotFound());

        verify(menuRepositoryPort, times(1)).findById(ID_MENU_MOCK);
        verify(restaurantRepositoryPort, times(1)).findById(request.getRestaurant());
        verify(menuRepositoryPort, never()).existsByName(request.getName());
        verify(restaurantRepositoryPort, never()).existsById(restaurant.get().getId());
        verify(menuRepositoryPort, never()).update(any(Menu.class));
    }

    @Test
    public void shouldThrowsNotFoundExceptionWhenIdRestaurantFromRequestOfMenuUpdateIsInexistent() throws Exception {

        //Arrange
        var request = MenuInsertDtoFixture.getWithRandomData().build();

        var menu = MenuFixture
            .getWithRandomData()
            .withId(ID_MENU_MOCK)
            .build();

        var restaurant = RestaurantFixture
            .getWithRandomData()
            .withId(request.getRestaurant())
            .build();

        when(menuRepositoryPort.findById(ID_MENU_MOCK))
            .thenReturn(Optional.of(menu));

        when(restaurantRepositoryPort.findById(anyLong()))
            .thenReturn(Optional.empty());

        //Action /Assert
        mockMvc.perform(
            put("/menu/update/{ID_MENU_MOCK)}", ID_MENU_MOCK)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.messageError").value("CADASTRO - Restaurant not found"));

        verify(menuRepositoryPort, times(1)).findById(ID_MENU_MOCK);
        verify(restaurantRepositoryPort, times(1)).findById(request.getRestaurant());
        verify(menuRepositoryPort, never()).existsByName(request.getName());
        verify(restaurantRepositoryPort, never()).existsById(restaurant.getId());
        verify(menuRepositoryPort, never()).update(any(Menu.class));
    }

    @Test
    public void shouldNotUpdateMenuIfPlateAlreadyExists() throws Exception {

        //Arrange
        var request = MenuInsertDtoFixture.getWithRandomData().build();

        var menu = MenuFixture
            .getWithRandomData()
            .withId(ID_MENU_MOCK)
            .build();

        var restaurant = Optional.of(RestaurantFixture
            .getWithRandomData()
            .withId(request.getRestaurant())
            .build());

        when(menuRepositoryPort.findById(ID_MENU_MOCK))
            .thenReturn(Optional.of(menu));

        when(restaurantRepositoryPort.findById(request.getRestaurant()))
            .thenReturn(restaurant);

        when(menuRepositoryPort.existsByName(request.getName()))
            .thenReturn(Boolean.TRUE);

        when(restaurantRepositoryPort.existsById(restaurant.get().getId()))
            .thenReturn(Boolean.TRUE);

        //Action /Assert
        mockMvc.perform(
            put("/menu/update/{ID_MENU_MOCK)}", ID_MENU_MOCK)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest());

        verify(menuRepositoryPort, times(1)).findById(ID_MENU_MOCK);
        verify(restaurantRepositoryPort, times(1)).findById(request.getRestaurant());
        verify(menuRepositoryPort, times(1)).existsByName(request.getName());
        verify(restaurantRepositoryPort, times(1)).existsById(restaurant.get().getId());
        verify(menuRepositoryPort, never()).update(any(Menu.class));
    }

    @Test
    public void shouldDeleteMenuOk() throws Exception {

        //Arrange
        var request = MenuInsertDtoFixture.getWithRandomData().build();

        var menu = MenuFixture
            .getWithRandomData()
            .withId(ID_MENU_MOCK)
            .build();

        when(menuRepositoryPort.findById(ID_MENU_MOCK))
            .thenReturn(Optional.of(menu));

        //Action /Assert
        mockMvc.perform(
            delete("/menu/delete/{ID_MENU_MOCK)}", ID_MENU_MOCK)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isOk());

        verify(menuRepositoryPort, times(1)).findById(ID_MENU_MOCK);
        verify(menuRepositoryPort, times(1)).delete(menu);
    }

    @Test
    public void shouldThrowsNotFoundExceptionIfMenuDoesntExistToDeleteMenuOk() throws Exception {

        //Arrange
        var request = MenuInsertDtoFixture.getWithRandomData().build();

        when(menuRepositoryPort.findById(ID_MENU_MOCK))
            .thenReturn(Optional.empty());

        //Action /Assert
        mockMvc.perform(
            delete("/menu/delete/{ID_MENU_MOCK)}", ID_MENU_MOCK)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.messageError").value("CADASTRO - Id not found"));

        verify(menuRepositoryPort, times(1)).findById(ID_MENU_MOCK);
        verify(menuRepositoryPort, never()).delete(any(Menu.class));
    }

    @Test
    public void shouldFindMenuOk() throws Exception {

        //Arrange
        var request = MenuInsertDtoFixture.getWithRandomData().build();

        var menu = MenuFixture
            .getWithRandomData()
            .withId(ID_MENU_MOCK)
            .build();

        when(menuRepositoryPort.findById(ID_MENU_MOCK))
            .thenReturn(Optional.of(menu));

        //Action /Assert
        mockMvc.perform(
            get("/menu/find/{ID_MENU_MOCK)}", ID_MENU_MOCK)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(ID_MENU_MOCK))
            .andExpect(jsonPath("$.name").value(menu.getName()))
            .andExpect(jsonPath("$.price").value(menu.getPrice()))
            .andExpect(jsonPath("$.restaurant").value(menu.getRestaurant()));

        verify(menuRepositoryPort, times(1)).findById(ID_MENU_MOCK);
    }

    @Test
    public void shouldThrowsNotFoundExceptionIfFindMenuDoesntFindMenu() throws Exception {

        //Arrange
        var request = MenuInsertDtoFixture.getWithRandomData().build();

        var menu = MenuFixture
            .getWithRandomData()
            .withId(ID_MENU_MOCK)
            .build();

        when(menuRepositoryPort.findById(ID_MENU_MOCK))
            .thenReturn(Optional.empty());

        //Action /Assert
        mockMvc.perform(
            get("/menu/find/{ID_MENU_MOCK)}", ID_MENU_MOCK)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.messageError").value("CADASTRO - Id not found"));

        verify(menuRepositoryPort, times(1)).findById(ID_MENU_MOCK);
    }
}
