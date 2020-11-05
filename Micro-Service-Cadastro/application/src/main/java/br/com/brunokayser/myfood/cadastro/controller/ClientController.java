package br.com.brunokayser.myfood.cadastro.controller;

import br.com.brunokayser.myfood.cadastro.mapper.ClientMapper;
import com.br.brunokayser.myfood.cadastro.service.ClientService;
import br.com.brunokayser.myfood.cadastro.dto.ClientDto;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public ResponseEntity insertClient(@RequestBody ClientDto clientDto) {

        try {
            return ResponseEntity.ok(clientService.insertClient(ClientMapper.toEntity(clientDto)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateClient(@PathVariable("id") Long id, @RequestBody ClientDto clientDto) {

        var updateClient = clientService.updateClient(ClientMapper.toEntity(clientDto, id));

        return Objects.nonNull(updateClient) ? ResponseEntity.ok(updateClient) : ResponseEntity.notFound().build();

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteClient(@PathVariable("id") Long id) {

        return clientService.deleteClient(id) ?
            ResponseEntity.ok().build() :
            ResponseEntity.notFound().build();
    }

    @GetMapping("/find/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id) {

        var client = clientService.findById(id);

        return client.isPresent() ?
            ResponseEntity.ok(client.get()) :
            ResponseEntity.notFound().build();
    }

}
