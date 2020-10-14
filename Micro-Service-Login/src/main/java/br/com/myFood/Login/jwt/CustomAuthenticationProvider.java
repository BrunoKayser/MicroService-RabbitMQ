package br.com.myFood.Login.jwt;

import br.com.myFood.Login.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;


@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final LoginRepository loginRepository;

    @Autowired
    public CustomAuthenticationProvider(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication)
        throws AuthenticationException {
        String email = authentication.getName();
        // You can get the password here
        String password = authentication.getCredentials().toString();

        var login = loginRepository.findByEmailAndPassword(email, password);

        if(login.isPresent()){
            return new UsernamePasswordAuthenticationToken(email, password);
        }else{
            return null;
        }

//        // Your custom authentication logic here
//        if (name.equals("admin") && password.equals("pwd")) {
//            Authentication auth = new UsernamePasswordAuthenticationToken(name,
//                password);
//
//            return auth;
//        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
