package com.br.brunokayser.myfood.cadastro.fixture;

import com.br.brunokayser.myfood.cadastro.domain.Menu;
import com.br.brunokayser.myfood.cadastro.domain.MenuUpdate;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

public class MenuUpdateFixture {

    private MenuUpdate menu;
    private MenuUpdateFixture(){}

    public static MenuUpdateFixture getWithRandomData() {
        MenuUpdateFixture builder = new MenuUpdateFixture();
        withRandomData(builder);
        return builder;
    }

    private static void withRandomData(MenuUpdateFixture builder) {
        builder.menu = new MenuUpdate();
        MenuUpdate menu = builder.menu;

        menu.setId(1L);
        menu.setName(RandomStringUtils.randomAlphabetic(10));
        menu.setPrice(RandomUtils.nextDouble());
        menu.setRestaurant(RandomUtils.nextLong());
    }

    public MenuUpdateFixture withId(Long param) {
        menu.setId(param);
        return this;
    }

    public MenuUpdateFixture withName(String param) {
        menu.setName(param);
        return this;
    }

    public MenuUpdateFixture withPrice(Double param) {
        menu.setPrice(param);
        return this;
    }


    public MenuUpdate build() {
        return menu;
    }

}
