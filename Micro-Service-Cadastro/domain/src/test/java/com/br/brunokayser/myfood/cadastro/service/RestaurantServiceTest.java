package com.br.brunokayser.myfood.cadastro.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.br.brunokayser.myfood.cadastro.domain.Restaurant;
import com.br.brunokayser.myfood.cadastro.fixture.RestaurantFixture;
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
class RestaurantServiceTest {

    @InjectMocks
    private RestaurantService restaurantService;

    @Mock
    private RestaurantRepositoryPort restaurantRepositoryPort;

    @Mock
    private ServiceValidator serviceValidator;

    private static final Long ID_MOCK = 1L;

    @Test
    void insertRestaurant() {

        //Arrange
        var restaurant = RestaurantFixture.getWithRandomData().withId(ID_MOCK).build();

        when(restaurantRepositoryPort.existsByEmailOrName(restaurant.getEmail(), restaurant.getName()))
            .thenReturn(Boolean.FALSE);

        when(restaurantRepositoryPort.save(restaurant))
            .thenReturn(restaurant);

        //Action
        var response = restaurantService.insertRestaurant(restaurant);

        //Assert
        verify(serviceValidator, times(1)).verifyIfExistsNameOrEmail(Boolean.FALSE);
        verify(restaurantRepositoryPort, times(1)).existsByEmailOrName(restaurant.getEmail(), restaurant.getName());

        assertEquals(ID_MOCK, response.getId());
        assertEquals(restaurant.getEmail(), response.getEmail());
        assertEquals(restaurant.getName(), response.getName());
        assertEquals(restaurant.getPassword(), response.getPassword());
    }

    @Test
    void updateRestaurant() {

        //Arrange
        var restaurant = RestaurantFixture
            .getWithRandomData()
            .withId(ID_MOCK)
            .build();

        when(restaurantRepositoryPort.findById(restaurant.getId()))
            .thenReturn(Optional.of(restaurant));

        when(restaurantRepositoryPort.existsByEmailOrName(restaurant.getEmail(), restaurant.getName()))
            .thenReturn(Boolean.FALSE);

        //Action
        var response = restaurantService.updateRestaurant(restaurant);

        //Assert
        var restaurantUpdateCaptur = ArgumentCaptor.forClass(Restaurant.class);

        verify(restaurantRepositoryPort, times(1)).findById(restaurant.getId());
        verify(serviceValidator, times(1)).validateIfFound(Boolean.FALSE);
        verify(serviceValidator, times(1)).verifyIfExistsNameOrEmail(Boolean.FALSE);
        verify(restaurantRepositoryPort, times(1)).update(restaurantUpdateCaptur.capture());

        assertEquals(ID_MOCK, restaurantUpdateCaptur.getValue().getId());
        assertEquals(restaurant.getPassword(), restaurantUpdateCaptur.getValue().getPassword());
        assertEquals(restaurant.getName(), restaurantUpdateCaptur.getValue().getName());
        assertEquals(restaurant.getEmail(), restaurantUpdateCaptur.getValue().getEmail());

        assertEquals(restaurant.getId(), response.getId());
        assertEquals(restaurant.getEmail(), response.getEmail());
        assertEquals(restaurant.getName(), response.getName());
        assertEquals(restaurant.getClass(), response.getClass());
    }

    @Test
    void deleteRestaurant() {

        //TODO ADICIONAR O SUREFIRE PARA RODAR MAIS DE UM TESTE AO MESMO TEMPO
        //Arrange

        //Action

        //Assert

    }

    @Test
    void findById() {

        //Arrange

        //Action

        //Assert

    }
}