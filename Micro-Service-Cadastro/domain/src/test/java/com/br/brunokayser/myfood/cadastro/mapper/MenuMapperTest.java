package com.br.brunokayser.myfood.cadastro.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class MenuMapperTest {

    @InjectMocks
    private MenuMapper menuMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void ShouldToOrderDtoOk(){

        //Arrange
        var idRestaurant = 1l;
        var idMenu = 1l;

        //Action
        var response = MenuMapper.toOrderDto(idMenu, idRestaurant);

        //Assert
        assertEquals(idRestaurant, response.getIdRestaurant());
        assertEquals(idMenu, response.getIdMenu());
    }
}
