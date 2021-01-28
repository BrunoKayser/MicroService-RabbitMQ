package fixture;

import br.com.brunokayser.myfood.cadastro.dto.ClientDto;
import com.br.brunokayser.myfood.cadastro.domain.Client;
import org.apache.commons.lang3.RandomStringUtils;

public class ClientDtoFixture {

    private ClientDto client;
    private ClientDtoFixture(){}

    public static ClientDtoFixture getWithRandomData() {
        ClientDtoFixture builder = new ClientDtoFixture();
        withRandomData(builder);
        return builder;
    }

    private static void withRandomData(ClientDtoFixture builder) {
        builder.client = new ClientDto();
        ClientDto client = builder.client;

        client.setId(1L);
        client.setName(RandomStringUtils.randomAlphabetic(10));
        client.setEmail("email@email.com.br");
        client.setPassword("12345abC");
    }

    public ClientDtoFixture withId(Long param) {
        client.setId(param);
        return this;
    }

    public ClientDtoFixture withoutId() {
        client.setId(null);
        return this;
    }

    public ClientDtoFixture withName(String param) {
        client.setName(param);
        return this;
    }

    public ClientDtoFixture withEmail(String param) {
        client.setEmail(param);
        return this;
    }

    public ClientDtoFixture withPassword(String param) {
        client.setPassword(param);
        return this;
    }

    public ClientDto build() {
        return client;
    }
}


