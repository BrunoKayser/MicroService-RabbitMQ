package br.com.brunokayser.myfood.cadastro.infraestructure.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.brunokayser.myfood.cadastro.dto.RestaurantDto;
import br.com.brunokayser.myfood.cadastro.fixture.RestaurantDtoFixture;
import br.com.brunokayser.myfood.cadastro.fixture.RestaurantFixture;
import br.com.brunokayser.myfood.cadastro.infraestructure.database.persistence.RestaurantRepository;
import com.br.brunokayser.myfood.cadastro.exception.InternalErrorException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RestaurantRepositoryPortImplTest {

    @InjectMocks
    private RestaurantRepositoryPortImpl restaurantRepositoryPort;

    @Mock
    private RestaurantRepository restaurantRepository;

    private static final Long ID_MOCK = 1L;
    private static final String NAME_MOCK = "Bruno Kayser";
    private static final String EMAIL_MOCK = "BrunoKayser@email.com";

    @Test
    public void shouldSaveRestaurantOk(){

        //Arrange
        var restaurant = RestaurantFixture.getWithRandomData().build();

        var restaurantSaveResponse = RestaurantDtoFixture
            .getWithRandomData()
            .withId(ID_MOCK)
            .withName(restaurant.getName())
            .withPassword(restaurant.getPassword())
            .withEmail(restaurant.getEmail())
            .build();

        when(restaurantRepository.save(any(RestaurantDto.class)))
            .thenReturn(restaurantSaveResponse);

        //Action
        var response = restaurantRepositoryPort.save(restaurant);

        //Assert
        verify(restaurantRepository).save(any(RestaurantDto.class));

        assertEquals(restaurant.getEmail(), response.getEmail());
        assertEquals(restaurant.getPassword(), response.getPassword());
        assertEquals(restaurant.getName(), response.getName());
        assertEquals(ID_MOCK, response.getId());
    }

    @Test
    public void shouldThrowsInternalErrorExceptionWhenSaveRestauranthasProblem(){

        //Arrange
        var restaurant = RestaurantFixture.getWithRandomData().build();

        when(restaurantRepository.save(any(RestaurantDto.class)))
            .thenThrow(new InternalErrorException("error.restaurant.repository"));

        //Action
        var exceptionResponse =assertThrows(InternalErrorException.class,
            ()-> restaurantRepositoryPort.save(restaurant));

        //Assert
        verify(restaurantRepository).save(any(RestaurantDto.class));

        assertEquals("error.restaurant.repository", exceptionResponse.getMessage());
    }

    @Test
    public void shouldFindRestaurantOk(){

        //Arrange
        var restaurant = RestaurantDtoFixture
            .getWithRandomData()
            .withId(ID_MOCK)
            .build();

        when(restaurantRepository.findById(ID_MOCK))
            .thenReturn(Optional.of(restaurant));

        //Action
        var response = restaurantRepositoryPort.findById(ID_MOCK);

        //Assert
        verify(restaurantRepository).findById(ID_MOCK);

        assertEquals(restaurant.getEmail(), response.get().getEmail());
        assertEquals(restaurant.getPassword(), response.get().getPassword());
        assertEquals(restaurant.getName(), response.get().getName());
        assertEquals(ID_MOCK, response.get().getId());
    }

    @Test
    public void shouldReturnOptionalEmptyWhenNotFindRestaurant() {

        //Arrange
        when(restaurantRepository.findById(ID_MOCK))
            .thenReturn(Optional.empty());

        //Action
        var response = restaurantRepositoryPort.findById(ID_MOCK);

        //Assert
        verify(restaurantRepository).findById(ID_MOCK);

        assertEquals(Optional.empty(), response);
    }

    @Test
    public void shouldThrowsInternalErrorExceptionWhenFindRestauranthasProblem(){

        //Arrange
        when(restaurantRepository.findById(ID_MOCK))
            .thenThrow(new InternalErrorException("error.restaurant.repository"));

        //Action
        var exceptionResponse =assertThrows(InternalErrorException.class,
            ()-> restaurantRepositoryPort.findById(ID_MOCK));

        //Assert
        verify(restaurantRepository).findById(ID_MOCK);

        assertEquals("error.restaurant.repository", exceptionResponse.getMessage());
    }

    @Test
    public void shouldDeleteRestaurantOk(){

        //Arrange
        var restaurant = RestaurantFixture
            .getWithRandomData()
            .build();

        //Action
        restaurantRepositoryPort.delete(restaurant);

        //Assert
        var restaurantDtoCaptur = ArgumentCaptor.forClass(RestaurantDto.class);
        verify(restaurantRepository).delete(restaurantDtoCaptur.capture());

        assertEquals(restaurant.getEmail(), restaurantDtoCaptur.getValue().getEmail());
        assertEquals(restaurant.getPassword(), restaurantDtoCaptur.getValue().getPassword());
        assertEquals(restaurant.getName(), restaurantDtoCaptur.getValue().getName());
        assertEquals(restaurant.getId(), restaurantDtoCaptur.getValue().getId());
    }

    @Test
    public void shouldThrowsInternalErrorExceptionWhenDeleteRestauranthasProblem(){

        //Arrange
        var restaurant = RestaurantFixture
            .getWithRandomData()
            .build();

        doThrow(InternalErrorException.class)
        .when(restaurantRepository)
            .delete(any(RestaurantDto.class));

        //Action
        var exceptionResponse =assertThrows(InternalErrorException.class,
            ()-> restaurantRepositoryPort.delete(restaurant));

        //Assert
        verify(restaurantRepository).delete(any(RestaurantDto.class));

        assertEquals("error.restaurant.repository", exceptionResponse.getMessage());
    }

    @Test
    public void shouldTestOkExistByEmailOrNameAndReturnTrue(){

        //Arrange
        when(restaurantRepository.existsByNameOrEmail(NAME_MOCK, EMAIL_MOCK))
            .thenReturn(Boolean.TRUE);

        //Action
        var booleanResponse=  restaurantRepositoryPort.existsByEmailOrName(EMAIL_MOCK, NAME_MOCK);

        //Assert
        verify(restaurantRepository).existsByNameOrEmail(NAME_MOCK, EMAIL_MOCK);

        assertTrue(booleanResponse);
    }

    @Test
    public void shouldTestOkExistByEmailOrNameAndReturnFalse(){

        //Arrange
        when(restaurantRepository.existsByNameOrEmail(NAME_MOCK, EMAIL_MOCK))
            .thenReturn(Boolean.FALSE);

        //Action
        var booleanResponse=  restaurantRepositoryPort.existsByEmailOrName(EMAIL_MOCK, NAME_MOCK);

        //Assert
        verify(restaurantRepository).existsByNameOrEmail(NAME_MOCK, EMAIL_MOCK);

        assertFalse(booleanResponse);
    }

    @Test
    public void shouldThrowsInternalErrorExceptionWhenExistByNameAndEmailHasProblem(){

        //Arrange
        doThrow(InternalErrorException.class)
        .when(restaurantRepository)
            .existsByNameOrEmail(NAME_MOCK, EMAIL_MOCK);

        //Action
        var exceptionResponse= assertThrows(InternalErrorException.class,
            () -> restaurantRepositoryPort.existsByEmailOrName(EMAIL_MOCK, NAME_MOCK));

        //Assert
        verify(restaurantRepository).existsByNameOrEmail(NAME_MOCK, EMAIL_MOCK);

        assertEquals("error.restaurant.repository",exceptionResponse.getMessage() );
    }

    @Test
    public void shouldUpdateOk(){

        //Arrange
        var restaurant = RestaurantFixture.getWithRandomData().build();

        //Action
        restaurantRepositoryPort.update(restaurant);

        //Assert
        verify(restaurantRepository).update(restaurant.getEmail(), restaurant.getName(), restaurant.getPassword(), restaurant.getId());
    }

    @Test
    public void shouldThrowsInternalErrorExceptionWhenUpdateHasProblem(){

        //Arrange
        var restaurant = RestaurantFixture.getWithRandomData().build();

        doThrow(InternalErrorException.class)
            .when(restaurantRepository)
            .update(restaurant.getEmail(), restaurant.getName(), restaurant.getPassword(), restaurant.getId());

        //Action
        var exceptionResponse= assertThrows(InternalErrorException.class,
            () -> restaurantRepositoryPort.update(restaurant));

        //Assert
        verify(restaurantRepository).update(restaurant.getEmail(), restaurant.getName(), restaurant.getPassword(), restaurant.getId());

        assertEquals("error.restaurant.repository",exceptionResponse.getMessage() );
    }

    @Test
    public void shouldTestOkExistBIdAndReturnTrue(){

        //Arrange
        when(restaurantRepository.existsById(ID_MOCK))
            .thenReturn(Boolean.TRUE);

        //Action
        var booleanResponse=  restaurantRepositoryPort.existsById(ID_MOCK);

        //Assert
        verify(restaurantRepository).existsById(ID_MOCK);

        assertTrue(booleanResponse);
    }

    @Test
    public void shouldTestOkExistBIdAndReturnFalse(){

        //Arrange
        when(restaurantRepository.existsById(ID_MOCK))
            .thenReturn(Boolean.FALSE);

        //Action
        var booleanResponse=  restaurantRepositoryPort.existsById(ID_MOCK);

        //Assert
        verify(restaurantRepository).existsById(ID_MOCK);

        assertFalse(booleanResponse);
    }

    @Test
    public void shouldThrowsInternalErrorExceptionWhenExistByIdHasProblem(){

        //Arrange
        doThrow(InternalErrorException.class)
            .when(restaurantRepository)
            .existsById(ID_MOCK);

        //Action
        var exceptionResponse= assertThrows(InternalErrorException.class,
            () -> restaurantRepositoryPort.existsById(ID_MOCK));

        //Assert
        verify(restaurantRepository).existsById(ID_MOCK);

        assertEquals("error.restaurant.repository",exceptionResponse.getMessage() );
    }


}
