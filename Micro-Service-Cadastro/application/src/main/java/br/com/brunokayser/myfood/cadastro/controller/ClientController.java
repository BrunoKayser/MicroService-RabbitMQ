package br.com.brunokayser.myfood.cadastro.controller;

import static br.com.brunokayser.myfood.cadastro.mapper.ClientMapper.toDomain;
import static br.com.brunokayser.myfood.cadastro.mapper.ClientMapper.toDto;

import br.com.brunokayser.myfood.cadastro.validator.RequestValidator;
import com.br.brunokayser.myfood.cadastro.service.ClientService;
import br.com.brunokayser.myfood.cadastro.dto.ClientDto;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/client")
public class ClientController {

    @Autowired
    private final ClientService clientService;

    @Autowired
    private final RequestValidator requestValidator;

    @PostMapping("/insert")
    public @ResponseBody
    ResponseEntity<ClientDto> insertClient(@RequestBody @Valid ClientDto clientDto) {

        requestValidator.validate(clientDto);

        var insertClient = clientService.insertClient(toDomain(clientDto));

        return new ResponseEntity<>(toDto(insertClient), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public @ResponseBody
    ResponseEntity<ClientDto> updateClient(@PathVariable("id") Long id, @RequestBody ClientDto clientDto) {

        requestValidator.validate(clientDto);

        var updatedClient = clientService.updateClient(toDomain(clientDto, id));

        return new ResponseEntity<>(toDto(updatedClient), HttpStatus.OK);

    }

    @DeleteMapping("/delete/{id}")
    public @ResponseBody
    ResponseEntity<Void> deleteClient(@PathVariable("id") Long id) {

        clientService.deleteClient(id);

       return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public @ResponseBody
    ResponseEntity<ClientDto> findById(@PathVariable("id") Long id) {

        var client = clientService.findById(id);

        return new ResponseEntity<>(toDto(client), HttpStatus.FOUND);
    }
}
