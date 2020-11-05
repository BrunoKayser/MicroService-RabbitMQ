package br.com.brunokayser.myfood.cadastro.configuration;

import com.br.brunokayser.myfood.cadastro.message.ClientSendMessage;
import com.br.brunokayser.myfood.cadastro.message.LoginSendMessage;
import com.br.brunokayser.myfood.cadastro.message.MenuSendMessage;
import com.br.brunokayser.myfood.cadastro.repository.ClientRepository;
import com.br.brunokayser.myfood.cadastro.repository.MenuRepository;
import com.br.brunokayser.myfood.cadastro.repository.RestaurantRepository;
import com.br.brunokayser.myfood.cadastro.service.ClientService;
import com.br.brunokayser.myfood.cadastro.service.MenuService;


import com.br.brunokayser.myfood.cadastro.service.RestaurantService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CadastroConfiguration {

    @Bean
    public ClientService clientService(ClientRepository clientRepository, ClientSendMessage clientSendMessage,
        LoginSendMessage loginSendMessage) {
        return new ClientService(clientRepository, clientSendMessage, loginSendMessage);
    }

    @Bean
    public MenuService menuService(MenuRepository menuRepository, RestaurantRepository restaurantRepository,
        MenuSendMessage menuSendMessage) {
        return new MenuService(menuRepository, restaurantRepository, menuSendMessage);
    }

    @Bean
    public RestaurantService restaurantService(RestaurantRepository restaurantRepository){
        return new RestaurantService(restaurantRepository);
    }

}
