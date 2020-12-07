package br.com.brunokayser.myfood.cadastro.configuration;


import com.br.brunokayser.myfood.cadastro.port.ClientRepositoryPort;
import com.br.brunokayser.myfood.cadastro.port.ClientSendMessage;
import com.br.brunokayser.myfood.cadastro.port.LoginSendMessage;
import com.br.brunokayser.myfood.cadastro.port.MenuRepositoryPort;
import com.br.brunokayser.myfood.cadastro.port.MenuSendMessage;
import com.br.brunokayser.myfood.cadastro.port.RestaurantRepositoryPort;
import com.br.brunokayser.myfood.cadastro.service.ClientService;
import com.br.brunokayser.myfood.cadastro.service.MenuService;
import com.br.brunokayser.myfood.cadastro.service.RestaurantService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CadastroConfiguration {

    @Bean
    public ClientService clientService(ClientRepositoryPort clientRepositoryPort, ClientSendMessage clientSendMessage,
        LoginSendMessage loginSendMessage) {
        return new ClientService(clientRepositoryPort, clientSendMessage, loginSendMessage);
    }

    @Bean
    public MenuService menuService(MenuRepositoryPort menuRepository, RestaurantRepositoryPort restaurantRepository,
        MenuSendMessage menuSendMessage) {
        return new MenuService(menuRepository, restaurantRepository, menuSendMessage);
    }

    @Bean
    public RestaurantService restaurantService(RestaurantRepositoryPort restaurantRepository){
        return new RestaurantService(restaurantRepository);
    }

}
