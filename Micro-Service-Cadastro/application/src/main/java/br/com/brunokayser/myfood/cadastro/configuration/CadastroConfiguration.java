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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class CadastroConfiguration {

    @Bean(name = "customMessageSource")
    public MessageSource messageSource() {

        final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:messages");
        return messageSource;
    }

//    @Bean(name = "customMessageSource")
//    public ResourceBundleMessageSource messageSource() {
//        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
//        messageSource.addBasenames("messages");
//        return messageSource;
//    }

    @Bean
    @Primary
    public LocalValidatorFactoryBean validatorBean(@Qualifier("customMessageSource") MessageSource messageSource) {

        final LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource);
        return bean;
    }

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
