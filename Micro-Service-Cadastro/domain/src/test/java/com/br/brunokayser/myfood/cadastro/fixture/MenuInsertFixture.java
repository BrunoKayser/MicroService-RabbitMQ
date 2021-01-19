package com.br.brunokayser.myfood.cadastro.fixture;

import com.br.brunokayser.myfood.cadastro.domain.MenuInsert;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

public class MenuInsertFixture {

    private MenuInsert menu;
    private MenuInsertFixture(){}

    public static MenuInsertFixture getWithRandomData() {
        MenuInsertFixture builder = new MenuInsertFixture();
        withRandomData(builder);
        return builder;
    }

    private static void withRandomData(MenuInsertFixture builder) {
        builder.menu = new MenuInsert();
        MenuInsert menu = builder.menu;

        menu.setName(RandomStringUtils.randomAlphabetic(10));
        menu.setPrice(RandomUtils.nextDouble());
        menu.setRestaurant(RandomUtils.nextLong());
    }

    public MenuInsert build() {
        return menu;
    }

}
