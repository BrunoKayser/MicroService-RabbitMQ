package com.br.brunokayser.myfood.cadastro.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message){
        super(message);
    }

    public NotFoundException(){};
}
