package unit;

import static org.mockito.ArgumentMatchers.any;

import br.com.brunokayser.myfood.cadastro.dto.ClientDto;
import br.com.brunokayser.myfood.cadastro.mapper.ClientMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

public class ClientMapperTest {

    @Test
    public void shouldToDomainClientOk(){

        var response = ClientMapper.toDomain(any(ClientDto.class));
    }
}
