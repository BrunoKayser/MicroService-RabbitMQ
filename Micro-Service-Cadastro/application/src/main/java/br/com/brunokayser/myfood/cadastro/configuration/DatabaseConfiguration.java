//package br.com.brunokayser.myfood.cadastro.configuration;
//
//import br.com.brunokayser.myfood.cadastro.infraestructure.database.ClientRepositoryImpl;
//import br.com.brunokayser.myfood.cadastro.infraestructure.database.ClientSendMessageImpl;
//import br.com.brunokayser.myfood.cadastro.infraestructure.database.LoginSendMessageImpl;
//import br.com.brunokayser.myfood.cadastro.infraestructure.database.MenuRepositoryImpl;
//import br.com.brunokayser.myfood.cadastro.infraestructure.database.MenuSendMessageImpl;
//import br.com.brunokayser.myfood.cadastro.infraestructure.database.RestaurantRepositoryImpl;
//import com.br.brunokayser.myfood.cadastro.port.ClientRepository;
//import com.br.brunokayser.myfood.cadastro.port.ClientSendMessage;
//import com.br.brunokayser.myfood.cadastro.port.LoginSendMessage;
//import com.br.brunokayser.myfood.cadastro.port.MenuRepository;
//import com.br.brunokayser.myfood.cadastro.port.MenuSendMessage;
//import com.br.brunokayser.myfood.cadastro.port.RestaurantRepository;
//import java.util.List;
//import javax.sql.DataSource;
//import org.jdbi.v3.core.Jdbi;
//import org.jdbi.v3.core.mapper.RowMapper;
//import org.jdbi.v3.core.spi.JdbiPlugin;
//import org.jdbi.v3.sqlobject.SqlObjectPlugin;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
//
//@Configuration
//public class DatabaseConfiguration {
//
//    @Bean
//    public Jdbi jdbi(DataSource dataSource, List<JdbiPlugin> plugins, List<RowMapper<?>> rowMappers) {
//        TransactionAwareDataSourceProxy proxy = new TransactionAwareDataSourceProxy(dataSource);
//        Jdbi jdbi = Jdbi.create(proxy);
//        plugins.forEach(jdbi::installPlugin);
//        rowMappers.forEach(jdbi::registerRowMapper);
//
//        return jdbi;
//    }
//
//    @Bean
//    public JdbiPlugin sqlObjectPlugin() {
//        return new SqlObjectPlugin();
//    }
//
////    @Bean
////    public ClientRepository clientRepository(Jdbi jdbi) {
////        return jdbi.onDemand(ClientRepositoryImpl.class);
////    }
//
//    @Bean
//    public ClientSendMessage clientSendMessage(Jdbi jdbi) {
//        return jdbi.onDemand(ClientSendMessageImpl.class);
//    }
//
//    @Bean
//    public LoginSendMessage loginSendMessage(Jdbi jdbi) {
//        return jdbi.onDemand(LoginSendMessageImpl.class);
//    }
//
//    @Bean
//    public MenuRepository menuRepository(Jdbi jdbi) {
//        return jdbi.onDemand(MenuRepositoryImpl.class);
//    }
//
//    @Bean
//    public MenuSendMessage menuSendMessage(Jdbi jdbi) {
//        return jdbi.onDemand(MenuSendMessageImpl.class);
//    }
//
//    @Bean
//    public RestaurantRepository restaurantRepository(Jdbi jdbi) {
//        return jdbi.onDemand(RestaurantRepositoryImpl.class);
//    }
//
//}
