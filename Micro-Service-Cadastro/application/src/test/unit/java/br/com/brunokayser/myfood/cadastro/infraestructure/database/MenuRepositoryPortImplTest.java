package br.com.brunokayser.myfood.cadastro.infraestructure.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.brunokayser.myfood.cadastro.dto.MenuDto;
import br.com.brunokayser.myfood.cadastro.fixture.MenuDtoFixture;
import br.com.brunokayser.myfood.cadastro.fixture.MenuFixture;
import br.com.brunokayser.myfood.cadastro.fixture.RestaurantDtoFixture;
import br.com.brunokayser.myfood.cadastro.infraestructure.database.persistence.MenuRepository;
import com.br.brunokayser.myfood.cadastro.exception.InternalErrorException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MenuRepositoryPortImplTest {

    @InjectMocks
    private MenuRepositoryPortImpl menuRepositoryPort;

    @Mock
    private MenuRepository menuRepository;

    private static final Long ID_MOCK = 1L;
    private static final String NAME_MOCK = "bruno Kayser";

    @Test
    public void shouldSaveMenuOk() {

        //Arrange
        var menu = MenuFixture.getWithRandomData().build();

        var menuResponseSave = MenuDtoFixture
            .getWithRandomData()
            .withId(ID_MOCK)
            .withName(menu.getName())
            .withPrice(menu.getPrice())
            .withRestaurant(RestaurantDtoFixture
                .getWithRandomData()
                .withEmail(menu.getRestaurant().getEmail())
                .withPassword(menu.getRestaurant().getPassword())
                .withName(menu.getRestaurant().getName())
                .withId(menu.getRestaurant().getId())
                .build())
            .build();

        when(menuRepository.save(any(MenuDto.class)))
            .thenReturn(menuResponseSave);

        //Action
        var response = menuRepositoryPort.save(menu);

        //Assert
        verify(menuRepository).save(any(MenuDto.class));

        assertEquals(ID_MOCK, response.getId());
        assertEquals(menu.getName(), response.getName());
        assertEquals(menu.getRestaurant(), response.getRestaurant());
        assertEquals(menu.getPrice(), response.getPrice());
    }

    @Test
    public void shouldThwrosInternalErrorExceptionWhensaveMenuHasProblem() {

        //Arrange
        var menu = MenuFixture.getWithRandomData().build();

        when(menuRepository.save(any(MenuDto.class)))
            .thenThrow(new InternalErrorException("error.menu.repository"));

        //Action
        var response = assertThrows(InternalErrorException.class,
            () -> menuRepositoryPort.save(menu));

        //Assert
        verify(menuRepository).save(any(MenuDto.class));

        assertEquals("error.menu.repository", response.getMessage());
    }

    @Test
    public void shouldFindMenuOk() {

        //Arrange
        var menu = MenuDtoFixture
            .getWithRandomData()
            .withId(ID_MOCK)
            .build();

        when(menuRepository.findById(ID_MOCK))
            .thenReturn(Optional.of(menu));

        //Action
        var response = menuRepositoryPort.findById(ID_MOCK);

        //Assert
        verify(menuRepository).findById(ID_MOCK);

        assertEquals(ID_MOCK, response.get().getId());
        assertEquals(menu.getName(), response.get().getName());
        assertEquals(menu.getRestaurant().getEmail(), response.get().getRestaurant().getEmail());
        assertEquals(menu.getRestaurant().getName(), response.get().getRestaurant().getName());
        assertEquals(menu.getRestaurant().getPassword(), response.get().getRestaurant().getPassword());
        assertEquals(menu.getRestaurant().getId(), response.get().getRestaurant().getId());
        assertEquals(menu.getPrice(), response.get().getPrice());
    }

    @Test
    public void shouldFindMenuWhenMenuIsEmpty() {

        //Arrange
        when(menuRepository.findById(ID_MOCK))
            .thenReturn(Optional.empty());

        //Action
        var response = menuRepositoryPort.findById(ID_MOCK);

        //Assert
        verify(menuRepository).findById(ID_MOCK);

        assertEquals(Optional.empty(), response);
        ;
    }

    @Test
    public void shouldThwrosInternalErrorExceptionWhenFindByIdHasProblem() {

        //Arrange
        when(menuRepository.findById(ID_MOCK))
            .thenThrow(new InternalErrorException("error.menu.repository"));

        //Action
        var responseException = assertThrows(InternalErrorException.class,
            () -> menuRepositoryPort.findById(ID_MOCK));

        //Assert
        verify(menuRepository).findById(ID_MOCK);

        assertEquals("error.menu.repository", responseException.getMessage());
    }

    @Test
    public void shouldDeleteMenuOk() {

        //Arrange
        var menu = MenuFixture
            .getWithRandomData()
            .build();

        //Action
        menuRepositoryPort.delete(menu);

        //Assert
        var menuDtoCaptur = ArgumentCaptor.forClass(MenuDto.class);
        verify(menuRepository).delete(menuDtoCaptur.capture());

        assertEquals(menu.getName(), menuDtoCaptur.getValue().getName());
        assertEquals(menu.getId(), menuDtoCaptur.getValue().getId());
        assertEquals(menu.getPrice(), menuDtoCaptur.getValue().getPrice());
        assertEquals(menu.getRestaurant().getEmail(), menuDtoCaptur.getValue().getRestaurant().getEmail());
        assertEquals(menu.getRestaurant().getId(), menuDtoCaptur.getValue().getRestaurant().getId());
        assertEquals(menu.getRestaurant().getName(), menuDtoCaptur.getValue().getRestaurant().getName());
        assertEquals(menu.getRestaurant().getPassword(), menuDtoCaptur.getValue().getRestaurant().getPassword());
    }

    @Test
    public void shouldThwrosInternalErrorExceptionWhenDeleteHasProblem() {

        //Arrange
        var menu = MenuFixture
            .getWithRandomData()
            .build();

        doThrow(InternalErrorException.class)
            .when(menuRepository)
            .delete(any(MenuDto.class));

        //Action
        var responseException = assertThrows(InternalErrorException.class,
            () -> menuRepositoryPort.delete(menu));

        //Assert
        verify(menuRepository).delete(any(MenuDto.class));

        assertEquals("error.menu.repository", responseException.getMessage());
    }

    @Test
    public void shouldVerifyThatExistsByNameIsTrue() {

        //Arrange
        when(menuRepository.existsByName(NAME_MOCK))
            .thenReturn(Boolean.TRUE);

        //Action
        var booleanResponse = menuRepositoryPort.existsByName(NAME_MOCK);

        //Assert
        verify(menuRepository).existsByName(NAME_MOCK);

        assertTrue(booleanResponse);
    }

    @Test
    public void shouldVerifyThatExistsByNameIsFalse() {

        //Arrange
        when(menuRepository.existsByName(NAME_MOCK))
            .thenReturn(Boolean.FALSE);

        //Action
        var booleanResponse = menuRepositoryPort.existsByName(NAME_MOCK);

        //Assert
        verify(menuRepository).existsByName(NAME_MOCK);

        assertFalse(booleanResponse);
    }

    @Test
    public void shouldThwrosInternalErrorExceptionWhenExistByNameHasProblem() {

        //Arrange
        doThrow(InternalErrorException.class)
            .when(menuRepository)
            .existsByName(NAME_MOCK);

        //Action
        var responseException = assertThrows(InternalErrorException.class,
            () -> menuRepositoryPort.existsByName(NAME_MOCK));

        //Assert
        verify(menuRepository).existsByName(NAME_MOCK);

        assertEquals("error.menu.repository", responseException.getMessage());
    }

    @Test
    public void shouldUpdateMenuOk() {

        //Arrange
        var menu = MenuFixture.getWithRandomData().build();

        //Action
        menuRepositoryPort.update(menu);

        //Assert
        verify(menuRepository).update(menu.getId(), menu.getName(), menu.getPrice(), menu.getRestaurant().getId());
    }

    @Test
    public void shouldThwrosInternalErrorExceptionWhenUpdateHasProblem() {

        //Arrange
        var menu = MenuFixture.getWithRandomData().build();

        doThrow(InternalErrorException.class)
            .when(menuRepository)
            .update(menu.getId(), menu.getName(), menu.getPrice(), menu.getRestaurant().getId());

        //Action
        var responseException = assertThrows(InternalErrorException.class,
            () -> menuRepositoryPort.update(menu));

        //Assert
        verify(menuRepository).update(menu.getId(), menu.getName(), menu.getPrice(), menu.getRestaurant().getId());

        assertEquals("error.menu.repository", responseException.getMessage());
    }

}
