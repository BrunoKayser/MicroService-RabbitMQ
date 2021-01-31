package br.com.brunokayser.myfood.cadastro.fixture;

import com.br.brunokayser.myfood.cadastro.domain.Restaurant;
import org.apache.commons.lang3.RandomStringUtils;

public class RestaurantFixture {
    private Restaurant restaurant;
    private RestaurantFixture(){}

    public static RestaurantFixture getWithRandomData() {
        RestaurantFixture builder = new RestaurantFixture();
        withRandomData(builder);
        return builder;
    }

    private static void withRandomData(RestaurantFixture builder) {
        builder.restaurant = new Restaurant();
        Restaurant restaurant = builder.restaurant;


        restaurant.setId(0L);
        restaurant.setName(RandomStringUtils.randomAlphabetic(10));
        restaurant.setEmail("email@gmail.com");
        restaurant.setPassword("abcd1234");
    }

    public RestaurantFixture withId(Long param) {
        restaurant.setId(param);
        return this;
    }

    public RestaurantFixture withoutId() {
        restaurant.setId(null);
        return this;
    }

    public RestaurantFixture withPassword(String password) {
        restaurant.setPassword(password);
        return this;
    }

    public RestaurantFixture withEmail(String email) {
        restaurant.setEmail(email);
        return this;
    }

    public RestaurantFixture withName(String name) {
        restaurant.setName(name);
        return this;
    }

    public Restaurant build() {
        return restaurant;
    }
}

