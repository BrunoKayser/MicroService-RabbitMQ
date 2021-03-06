package br.com.brunokayser.myfood.cadastro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
//@EnableEurekaClient
@EntityScan(basePackages = {"br.com.brunokayser.myfood.cadastro.dto"})
public class CadastroApplication {

	public static void main(String[] args) {
		SpringApplication.run(CadastroApplication.class, args);
	}

}
