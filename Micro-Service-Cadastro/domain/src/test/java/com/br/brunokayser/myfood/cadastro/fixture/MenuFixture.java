package com.br.brunokayser.myfood.cadastro.fixture;

import com.br.brunokayser.myfood.cadastro.domain.Menu;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

public class MenuFixture {

    private Menu menu;
    private MenuFixture(){}

    public static MenuFixture getWithRandomData() {
        MenuFixture builder = new MenuFixture();
        withRandomData(builder);
        return builder;
    }

    private static void withRandomData(MenuFixture builder) {
        builder.menu = new Menu();
        Menu menu = builder.menu;

        menu.setId(1L);
        menu.setName(RandomStringUtils.randomAlphabetic(10));
        menu.setPrice(RandomUtils.nextDouble());
        menu.setRestaurant(RestaurantFixture.getWithRandomData().build());
    }

    public MenuFixture withId(Long param) {
        menu.setId(param);
        return this;
    }

    public MenuFixture withName(String param) {
        menu.setName(param);
        return this;
    }

    public MenuFixture withPrice(Double param) {
        menu.setPrice(param);
        return this;
    }


    public Menu build() {
        return menu;
    }

}
