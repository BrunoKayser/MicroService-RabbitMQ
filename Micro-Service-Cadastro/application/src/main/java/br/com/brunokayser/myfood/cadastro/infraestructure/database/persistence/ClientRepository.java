package br.com.brunokayser.myfood.cadastro.infraestructure.database.persistence;

import br.com.brunokayser.myfood.cadastro.dto.ClientDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ClientRepository extends JpaRepository<ClientDto, Long> {

    boolean existsByEmail(String name);

    @Modifying
    @Transactional
    @Query(value = "UPDATE cadastro.tb_client tbc SET tbc.name = :name, tbc.email = :email, tbc.password = :password WHERE tbc.id = :id", nativeQuery = true)
    void update(
        @Param(value = "email") String email,
        @Param(value = "name") String name,
        @Param(value = "password") String password,
        @Param(value = "id") Long id);

}