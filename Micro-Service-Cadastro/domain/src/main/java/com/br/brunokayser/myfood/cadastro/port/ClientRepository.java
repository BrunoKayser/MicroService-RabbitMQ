package com.br.brunokayser.myfood.cadastro.port;

import com.br.brunokayser.myfood.cadastro.domain.Client;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository  {

    Client save(Client client);

    Optional<Client> findById(Long id);

    void delete(Client client);
}
