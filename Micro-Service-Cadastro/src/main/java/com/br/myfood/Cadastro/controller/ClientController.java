package com.br.myfood.Cadastro.controller;

import com.br.myfood.Cadastro.dto.ClientDto;
import com.br.myfood.Cadastro.mapper.ClientMapper;
import com.br.myfood.Cadastro.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/insert")
    public ResponseEntity insertClient(@RequestBody ClientDto clientDto){

        try{
            return ResponseEntity.ok(clientService.insertClient(ClientMapper.toEntity(clientDto)));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e);
        }

    }

}
