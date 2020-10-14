package br.com.myFood.Login.mapper;

import br.com.myFood.Login.dto.LoginDto;
import br.com.myFood.Login.entity.Login;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class LoginMapper {

    public static Login toEntity(LoginDto loginDto){

        var login = new ModelMapper().map(loginDto, Login.class);
        login.setId(null);
        return login;

    }
}
