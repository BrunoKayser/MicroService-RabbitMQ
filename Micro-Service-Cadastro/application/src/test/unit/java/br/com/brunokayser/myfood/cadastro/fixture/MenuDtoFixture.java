package br.com.brunokayser.myfood.cadastro.fixture;

import br.com.brunokayser.myfood.cadastro.dto.MenuDto;
import br.com.brunokayser.myfood.cadastro.dto.RestaurantDto;
import com.br.brunokayser.myfood.cadastro.domain.Menu;
import com.br.brunokayser.myfood.cadastro.domain.Restaurant;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

public class MenuDtoFixture {

    private MenuDto menuDto;
    private MenuDtoFixture(){}

    public static MenuDtoFixture getWithRandomData() {
        MenuDtoFixture builder = new MenuDtoFixture();
        withRandomData(builder);
        return builder;
    }

    private static void withRandomData(MenuDtoFixture builder) {
        builder.menuDto = new MenuDto();
        MenuDto menu = builder.menuDto;

        menu.setId(1L);
        menu.setName(RandomStringUtils.randomAlphabetic(10));
        menu.setPrice(RandomUtils.nextDouble());
        menu.setRestaurant(RestaurantDtoFixture.getWithRandomData().build());
    }

    public MenuDtoFixture withName(String param) {
        menuDto.setName(param);
        return this;
    }

    public MenuDtoFixture withPrice(Double price) {
        menuDto.setPrice(price);
        return this;
    }
    public MenuDtoFixture withId(Long id) {
        menuDto.setId(id);
        return this;
    }

    public MenuDtoFixture withRestaurant(RestaurantDto restaurant){
        menuDto.setRestaurant(restaurant);
        return this;
    }

    public MenuDto build() {
        return menuDto;
    }
}


