package com.br.brunokayser.myfood.cadastro.fixture;

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
        restaurant.setEmail(RandomStringUtils.randomAlphanumeric(10));
        restaurant.setPassword(RandomStringUtils.randomAlphanumeric(10));
    }

    public RestaurantFixture withId(Long param) {
        restaurant.setId(param);
        return this;
    }

    public RestaurantFixture withoutId() {
        restaurant.setId(null);
        return this;
    }


    public Restaurant build() {
        return restaurant;
    }
}

