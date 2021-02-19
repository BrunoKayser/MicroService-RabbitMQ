package br.com.brunokayser.myfood.cadastro.fixture;

import br.com.brunokayser.myfood.cadastro.dto.MenuInsertDto;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

public class MenuInsertDtoFixture {

    private MenuInsertDto menuInsertDto;
    private MenuInsertDtoFixture(){}

    public static MenuInsertDtoFixture getWithRandomData() {
        MenuInsertDtoFixture builder = new MenuInsertDtoFixture();
        withRandomData(builder);
        return builder;
    }

    private static void withRandomData(MenuInsertDtoFixture builder) {
        builder.menuInsertDto = new MenuInsertDto();
        MenuInsertDto menuInsertDto = builder.menuInsertDto;

        menuInsertDto.setName(RandomStringUtils.randomAlphabetic(10));
        menuInsertDto.setPrice(RandomUtils.nextDouble());
        menuInsertDto.setRestaurant(RandomUtils.nextLong());
    }

    public MenuInsertDto build() {
        return menuInsertDto;
    }

    public MenuInsertDtoFixture withPrice(Double price){
        menuInsertDto.setPrice(price);
        return this;
    }
}


