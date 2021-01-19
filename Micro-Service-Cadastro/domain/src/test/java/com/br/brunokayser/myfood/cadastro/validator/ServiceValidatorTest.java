package com.br.brunokayser.myfood.cadastro.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class ServiceValidatorTest {

    @InjectMocks
    private ServiceValidator serviceValidator;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        //serviceValidator = new ServiceValidator();
    }

    @Test
    public void shouldValidateIfFoundOk(){

//        var service = new ServiceValidator();

        serviceValidator.validateIfFound(Boolean.FALSE);
    }
}
