package br.com.myFood.Login.repository;

import br.com.myFood.Login.entity.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginRepository extends JpaRepository<Login,Long> {

    @Query("SELECT 1 FROM Login AS l " +
            "WHERE l.email = ?1 AND l.password = ?2 ")
    Optional<Login> findByEmailAndPassword(String email, String password);

}
