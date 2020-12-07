package com.br.brunokayser.myfood.cadastro.port;

import com.br.brunokayser.myfood.cadastro.domain.Client;

public interface ClientRepositoryPort {

    Client save(Client client);

    Client findById(Long id);

    void delete(Client client);

    Boolean existsByEmail(String email);

}
