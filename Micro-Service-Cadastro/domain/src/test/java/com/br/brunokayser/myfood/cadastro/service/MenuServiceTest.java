package com.br.brunokayser.myfood.cadastro.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.br.brunokayser.myfood.cadastro.domain.Menu;
import com.br.brunokayser.myfood.cadastro.fixture.MenuFixture;
import com.br.brunokayser.myfood.cadastro.fixture.MenuInsertFixture;
import com.br.brunokayser.myfood.cadastro.fixture.MenuUpdateFixture;
import com.br.brunokayser.myfood.cadastro.fixture.RestaurantFixture;
import com.br.brunokayser.myfood.cadastro.port.MenuRepositoryPort;
import com.br.brunokayser.myfood.cadastro.port.MenuSendMessage;
import com.br.brunokayser.myfood.cadastro.port.RestaurantRepositoryPort;
import com.br.brunokayser.myfood.cadastro.validator.ServiceValidator;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MenuServiceTest {

    @InjectMocks
    private MenuService menuService;

    @Mock
    private MenuRepositoryPort menuRepositoryPort;

    @Mock
    private RestaurantRepositoryPort restaurantRepositoryPort;

    @Mock
    private MenuSendMessage menuSendMessage;

    @Mock
    private ServiceValidator serviceValidator;

    private static final Long ID_MOCK = 1L;

    @Test
    void insertMenu() {

        //Arrange
        var menuInsert = MenuInsertFixture.getWithRandomData().build();

        var restaurant = RestaurantFixture
            .getWithRandomData()
            .withId(menuInsert.getRestaurant())
            .build();

        var menu = MenuFixture
            .getWithRandomData()
            .withId(ID_MOCK)
            .withName(menuInsert.getName())
            .withPrice(menuInsert.getPrice())
            .build();

        when(restaurantRepositoryPort.findById(menuInsert.getRestaurant()))
            .thenReturn(Optional.of(restaurant));

        when(menuRepositoryPort.existsByName(menuInsert.getName()))
            .thenReturn(Boolean.FALSE);

        when(restaurantRepositoryPort.existsById(menuInsert.getRestaurant()))
            .thenReturn(Boolean.FALSE);

        when(menuRepositoryPort.save(any(Menu.class))).thenReturn(menu);

        //Action
        var response = menuService.insertMenu(menuInsert);

        //Assert
        var mapperCaptur = ArgumentCaptor.forClass(Menu.class);
        verify(menuRepositoryPort, times(1)).save(mapperCaptur.capture());

        verify(restaurantRepositoryPort, times(1)).findById(menuInsert.getRestaurant());
        verify(menuRepositoryPort, times(1)).existsByName(menuInsert.getName());
        verify(restaurantRepositoryPort, times(1)).existsById(menuInsert.getRestaurant());
        verify(serviceValidator, times(1)).validateIfFound(Boolean.FALSE);
        verify(serviceValidator, times(1)).validateIfExistsFoodPlate(Boolean.FALSE, Boolean.FALSE);
        verify(menuSendMessage, times(1)).sendMessage(any());

        assertNull( mapperCaptur.getValue().getId());
        assertEquals(menuInsert.getName(), mapperCaptur.getValue().getName());
        assertEquals(menuInsert.getPrice(), mapperCaptur.getValue().getPrice());
        assertEquals(menuInsert.getRestaurant(), mapperCaptur.getValue().getRestaurant().getId());
        assertNotNull(response.getId());
        assertEquals(menu.getName(), response.getName());
        assertEquals(menu.getPrice(), response.getPrice());
        assertEquals(menu.getRestaurant(), response.getRestaurant());
    }

    @Test
    void shouldVerifyTheMenuUpdatedOk() {

        //Arrange
        var menuToUpdate = MenuUpdateFixture.getWithRandomData().build();

        var menuFound = Optional.of(MenuFixture
            .getWithRandomData()
            .withId(menuToUpdate.getId())
            .withIdRestaurant(menuToUpdate.getRestaurant())
            .build());

        var restaurant = RestaurantFixture.getWithRandomData().withId(menuToUpdate.getRestaurant()).build();

        when(menuRepositoryPort.findById(menuToUpdate.getId()))
            .thenReturn(menuFound);

        when(restaurantRepositoryPort.findById(menuToUpdate.getRestaurant()))
            .thenReturn(Optional.of(restaurant));

        when(menuRepositoryPort.existsByName(menuToUpdate.getName()))
            .thenReturn(Boolean.FALSE);

        when(restaurantRepositoryPort.existsById(menuFound.get().getRestaurant().getId()))
            .thenReturn(Boolean.FALSE);

        //Action
        menuService.updateMenu(menuToUpdate);

        //Assert
        var menuUpdatedCaptor = ArgumentCaptor.forClass(Menu.class);

        verify(menuRepositoryPort, times(1)).findById(menuToUpdate.getId());
        verify(restaurantRepositoryPort, times(1)).findById(menuToUpdate.getRestaurant());
        verify(serviceValidator, times(1)).validateIfFound(Boolean.FALSE, Boolean.FALSE, menuToUpdate);
        verify(menuRepositoryPort, times(1)).existsByName(menuToUpdate.getName());
        verify(restaurantRepositoryPort, times(1)).existsById(menuFound.get().getRestaurant().getId());
        verify(serviceValidator, times(1)).validateIfExistsFoodPlate(Boolean.FALSE, Boolean.FALSE);
        verify(menuRepositoryPort, times(1)).update(menuUpdatedCaptor.capture());

        assertEquals(menuToUpdate.getId(), menuUpdatedCaptor.getValue().getId());
        assertEquals(menuToUpdate.getName(), menuUpdatedCaptor.getValue().getName());
        assertEquals(menuToUpdate.getPrice(), menuUpdatedCaptor.getValue().getPrice());
        assertEquals(restaurant, menuUpdatedCaptor.getValue().getRestaurant());
    }

    @Test
    void shouldDeleteMenuOk() {

        //Arrange
        var menu = MenuFixture
            .getWithRandomData()
            .withId(ID_MOCK)
            .build();

        when(menuRepositoryPort.findById(ID_MOCK))
            .thenReturn(Optional.of(menu));

        //Action
        menuService.deleteMenu(ID_MOCK);

        //Assert
        verify(menuRepositoryPort, times(1)).findById(ID_MOCK);
        verify(serviceValidator, times(1)).validateIfFound(Boolean.FALSE);
    }

    @Test
    void findById() {

        //Arrange
        var menu = MenuFixture
            .getWithRandomData()
            .withId(ID_MOCK)
            .build();

        when(menuRepositoryPort.findById(ID_MOCK))
            .thenReturn(Optional.of(menu));

        //Action
        var response = menuService.findById(ID_MOCK);

        //Assert
        verify(menuRepositoryPort, times(1)).findById(ID_MOCK);
        verify(serviceValidator, times(1)).validateIfFound(Boolean.FALSE);

        assertEquals(ID_MOCK, response.getId());
        assertEquals(menu.getRestaurant(), response.getRestaurant());
        assertEquals(menu.getPrice(), response.getPrice());
        assertEquals(menu.getName(), response.getName());
    }
}