//package com.br.brunokayser.myfood.cadastro.mapper;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//import com.br.brunokayser.myfood.cadastro.fixture.ClientFixture;
//import com.br.brunokayser.myfood.cadastro.mapper.ClientMapper;
//import org.junit.jupiter.api.Test;
//
//public class ClientMapperTest {
//
//    @Test
//    public void shouldBuildClientToUpdateOk() {
//
//        //Arrange
//        var clientUpdate = ClientFixture.getWithRandomData().build();
//        var clientFound = ClientFixture.getWithRandomData().build();
//
//        //Action
//        var response = ClientMapper.buildClientToUpdate(clientUpdate, clientFound);
//
//        //Assert
//        assertEquals(response, clientUpdate);
//    }
//}
