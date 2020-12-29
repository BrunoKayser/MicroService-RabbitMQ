package br.com.brunokayser.myfood.cadastro.infraestructure.database;

import br.com.brunokayser.myfood.cadastro.infraestructure.database.persistence.ClientRepository;
import br.com.brunokayser.myfood.cadastro.mapper.ClientMapper;
import com.br.brunokayser.myfood.cadastro.domain.Client;
import com.br.brunokayser.myfood.cadastro.exception.InternalErrorException;
import com.br.brunokayser.myfood.cadastro.port.ClientRepositoryPort;
import java.util.Optional;
import javax.persistence.TransactionRequiredException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
class ClientRepositoryPortImpl implements ClientRepositoryPort {

    //TODO validar nos testes unitários as mensages das exceptions

    private final ClientRepository clientRepository;

    private static final String TAG = "CADASTRO - ";

    @Override
    public Client save(Client client) {
        try {
            return ClientMapper.toDomain(clientRepository.save(ClientMapper.toDto(client)));
        } catch (Exception e) {
            log.error(TAG + "Error in repository trying to save client {}", client);
            throw new InternalErrorException("error.save.client.repository");
        }
    }

    @Override
    public Optional<Client> findById(Long id) {
        try {
            var client = ClientMapper.toDomain(clientRepository.findById(id).orElse(null));
            return Optional.ofNullable(client);

        } catch (Exception e) {
            log.error(TAG + "Error in repository trying to find client with id : {}", id);
            throw new InternalErrorException("error.find.client.repository");
        }
    }

    @Override
    public void delete(Client client) {
        try {
            clientRepository.delete(ClientMapper.toDto(client));
        } catch (Exception e) {
            log.error(TAG + "Error in repository trying to delete client {}", client);
            throw new InternalErrorException("error.delete.client.repository");
        }
    }

    @Override
    public Boolean existsByEmail(String email) {
        try {
            return clientRepository.existsByEmail(email);
        } catch (Exception e) {
            log.error(TAG + "Error in repository trying to verify email {}", email);
            throw new InternalErrorException("error.update.client.repository");
        }
    }

    @Override
    public void update(Client client) {
        try {
            clientRepository.update(client.getEmail(), client.getName(), client.getPassword(), client.getId());
        } catch (TransactionRequiredException e) {
            e.printStackTrace();
        }
    }
}
