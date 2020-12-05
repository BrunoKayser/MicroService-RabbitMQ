package com.br.brunokayser.myfood.cadastro.port;

import com.br.brunokayser.myfood.cadastro.domain.LoginDto;

public interface LoginSendMessage {

    void sendMessage(LoginDto loginDto);

}
