package br.com.brunokayser.myfood.cadastro.infraestructure.database;

import br.com.brunokayser.myfood.cadastro.dto.ClientDto;
import br.com.brunokayser.myfood.cadastro.mapper.ClientMapper;
import com.br.brunokayser.myfood.cadastro.domain.Client;
import com.br.brunokayser.myfood.cadastro.port.ClientRepository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepositoryImpl extends JpaRepository<ClientDto, Long>, ClientRepository {

    @Override
    @Query(value = "INSERT INTO Cadastro.tb_cliente (name, email, password) VALUES (?1.name ,?1.email, client.password)" , nativeQuery = true)
    Client save(Client client);

    @Override
    @Query(value = "SELECT * cadastro.tb_cliente cadastro WHERE cadastro.id = ?1" , nativeQuery = true)
    Optional<ClientDto> findById(Long id);


    @Override
    void delete(Client client);

}
