package com.br.brunokayser.myfood.cadastro.port;

import com.br.brunokayser.myfood.cadastro.domain.MenuOrder;

public interface MenuSendMessage {

    void sendMessage(MenuOrder menuOrderDto);

}
