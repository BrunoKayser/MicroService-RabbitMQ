package com.br.brunokayser.myfood.cadastro.port;

import com.br.brunokayser.myfood.cadastro.domain.ClientOrderDto;

public interface ClientSendMessage {

    void sendMessage(ClientOrderDto clientOrderDto);

}
