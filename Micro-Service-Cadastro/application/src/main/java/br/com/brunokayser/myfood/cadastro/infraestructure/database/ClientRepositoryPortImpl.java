package br.com.brunokayser.myfood.cadastro.infraestructure.database;

import br.com.brunokayser.myfood.cadastro.dto.ClientDto;
import br.com.brunokayser.myfood.cadastro.infraestructure.database.persistence.ClientRepository;
import br.com.brunokayser.myfood.cadastro.mapper.ClientMapper;
import com.br.brunokayser.myfood.cadastro.domain.Client;
import com.br.brunokayser.myfood.cadastro.exception.NotFoundException;
import com.br.brunokayser.myfood.cadastro.port.ClientRepositoryPort;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class ClientRepositoryPortImpl implements ClientRepositoryPort {

    private final ClientRepository clientRepository;

    @Override
    public Client save(Client client) {
        return ClientMapper.toDomain(clientRepository.save(ClientMapper.toDto(client)));
    }

    @Override
    public Optional<Client> findById(Long id) {
        var client = ClientMapper.toDomain(clientRepository.findById(id).orElse(null));

        return Optional.ofNullable(client);
    }

    @Override
    public void delete(Client client){
        clientRepository.delete(ClientMapper.toDto(client));
    }

    @Override
    public Boolean existsByEmail(String email){
        return clientRepository.existsByEmail(email);
    }
}