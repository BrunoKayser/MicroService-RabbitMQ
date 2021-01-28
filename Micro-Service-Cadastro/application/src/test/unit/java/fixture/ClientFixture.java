package fixture;

import com.br.brunokayser.myfood.cadastro.domain.Client;
import org.apache.commons.lang3.RandomStringUtils;

public class ClientFixture {

    private Client client;
    private ClientFixture(){}

    public static ClientFixture getWithRandomData() {
        ClientFixture builder = new ClientFixture();
        withRandomData(builder);
        return builder;
    }

    private static void withRandomData(ClientFixture builder) {
        builder.client = new Client();
        Client client = builder.client;

        client.setId(1L);
        client.setName(RandomStringUtils.randomAlphabetic(10));
        client.setEmail(RandomStringUtils.randomAlphabetic(10));
        client.setPassword(RandomStringUtils.randomAlphabetic(10));
    }

    public ClientFixture withId(Long param) {
        client.setId(param);
        return this;
    }

    public ClientFixture withoutId() {
        client.setId(null);
        return this;
    }

    public ClientFixture withName(String param) {
        client.setName(param);
        return this;
    }

    public ClientFixture withEmail(String param) {
        client.setEmail(param);
        return this;
    }

    public ClientFixture withPassword(String param) {
        client.setPassword(param);
        return this;
    }

    public Client build() {
        return client;
    }
}


