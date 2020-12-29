package br.com.brunokayser.myfood.cadastro.validator;

import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

import br.com.brunokayser.myfood.cadastro.dto.ClientDto;
import br.com.brunokayser.myfood.cadastro.dto.RestaurantDto;
import com.br.brunokayser.myfood.cadastro.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RequestValidator {

    private static final String REGEX_EMAIL = "^[a-zA-Z0-9.!#$%&'*+\\/=?^_`\\{|\\}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";
    private static final String REGEX_PASSWORD = "^(?=.*?[a-zA-Z])(?=(?:.*?\\d))[a-zA-Z0-9\\d]{6,100}";

    public void validate(ClientDto clientDto){

        if(of(clientDto).isPresent()){
            validateAtributes(clientDto.getEmail(), clientDto.getPassword());
        }else{
            throw new BadRequestException("request.cant.null");
        }
    }

    public void validate(RestaurantDto restaurantDto){

        if(of(restaurantDto).isPresent()){
            validateAtributes(restaurantDto.getEmail(), restaurantDto.getPassword());
        }else{
            throw new BadRequestException("request.cant.null");
        }
    }
    
    private void validateAtributes(String email, String password){
        ofNullable(email).ifPresent(this::validateEmail);
        ofNullable(password).ifPresent(this::validatePassword);
    }

    private void validateEmail(String email){
        if(!email.matches(REGEX_EMAIL)){
            throw new BadRequestException("invalid.email.format");
        }
    }

    private void validatePassword(String password){
        if(!password.matches(REGEX_PASSWORD)){
            throw new BadRequestException("invalid.password.format");
        }
    }
}
