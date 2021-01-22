package com.br.brunokayser.myfood.cadastro.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.br.brunokayser.myfood.cadastro.exception.BadRequestException;
import com.br.brunokayser.myfood.cadastro.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ServiceValidatorTest {

    @InjectMocks
    private ServiceValidator serviceValidator;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldValidateIfFoundOk(){

        //Arrange/Action/Assert
        serviceValidator.validateIfFound(Boolean.FALSE);
    }

    @Test
    public void shouldThrowsNotFoundExceptionWhenIsFalseInValidateIfFoundOk(){

        //Arrange/Action/Assert
        var exception = assertThrows(NotFoundException.class, () -> serviceValidator.validateIfFound(Boolean.TRUE));

        assertEquals("id.not.found", exception.getMessage());
    }

    @Test
    public void shouldValidateIfExistsFoodPlateOk(){
        //Arrange/Action/Assert
        serviceValidator.validateIfExistsFoodPlate(Boolean.FALSE, Boolean.FALSE);
    }

    @Test
    public void shouldThrowsBadRequestExceptionWhenIsFalseInValidateIfExistsFoodPlate(){

        //Arrange/Action/Assert
        var exception = assertThrows(BadRequestException.class, () ->
             serviceValidator.validateIfExistsFoodPlate(Boolean.TRUE, Boolean.TRUE));

        assertEquals("menu.already.exists", exception.getMessage());
    }

}
