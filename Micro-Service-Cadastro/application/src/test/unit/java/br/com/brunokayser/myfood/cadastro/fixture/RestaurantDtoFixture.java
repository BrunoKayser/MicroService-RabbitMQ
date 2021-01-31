package br.com.brunokayser.myfood.cadastro.fixture;

import br.com.brunokayser.myfood.cadastro.dto.RestaurantDto;
import org.apache.commons.lang3.RandomStringUtils;

public class RestaurantDtoFixture {

    private RestaurantDto restaurant;
    private RestaurantDtoFixture(){}

    public static RestaurantDtoFixture getWithRandomData() {
        RestaurantDtoFixture builder = new RestaurantDtoFixture();
        withRandomData(builder);
        return builder;
    }

    private static void withRandomData(RestaurantDtoFixture builder) {
        builder.restaurant = new RestaurantDto();
        RestaurantDto restaurant = builder.restaurant;

        restaurant.setId(1L);
        restaurant.setName(RandomStringUtils.randomAlphabetic(10));
        restaurant.setEmail("email@email.com.br");
        restaurant.setPassword("12345abC");
    }

    public RestaurantDtoFixture withId(Long param) {
        restaurant.setId(param);
        return this;
    }

    public RestaurantDtoFixture withoutId() {
        restaurant.setId(null);
        return this;
    }

    public RestaurantDtoFixture withName(String param) {
        restaurant.setName(param);
        return this;
    }

    public RestaurantDtoFixture withEmail(String param) {
        restaurant.setEmail(param);
        return this;
    }

    public RestaurantDtoFixture withPassword(String param) {
        restaurant.setPassword(param);
        return this;
    }

    public RestaurantDto build() {
        return restaurant;
    }
}


