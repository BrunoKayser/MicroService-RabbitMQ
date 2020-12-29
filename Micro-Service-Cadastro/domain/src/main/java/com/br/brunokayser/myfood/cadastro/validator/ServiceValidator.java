package com.br.brunokayser.myfood.cadastro.validator;

import com.br.brunokayser.myfood.cadastro.exception.BadRequestException;
import com.br.brunokayser.myfood.cadastro.exception.NotFoundException;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder
@RequiredArgsConstructor
public class ServiceValidator {

    private static final String TAG = "CADASTRO - ";

    public void validateIfFound(boolean condition){
        if (condition) {
            log.error(TAG + "This id doesn't exists");
            throw new NotFoundException("id.not.found");
        }
    }

    public void verifyIfExistsByEmail(boolean condition){
        if (condition) {
            log.error(TAG + " This client already exists");
            throw new BadRequestException("client.already.exists");
        }
    }

    public void verifyIfExistsNameOrEmail(boolean condition){
        if(condition){
            log.error(TAG + " This restaurant name or email already exists");
            throw new BadRequestException("restaurant.name.email.already.exists");
        }
    }
}
