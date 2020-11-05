package br.com.brunokayser.myfood.cadastro.controller;

import com.br.myfood.Cadastro.dto.RestaurantDto;
import br.com.brunokayser.myfood.cadastro.mapper.RestaurantMapper;
import com.br.myfood.Cadastro.service.RestaurantService;
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
@RequestMapping("/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping("/insert")
    public ResponseEntity insertRestaurant(@RequestBody RestaurantDto clientDto) {

        try {
            return ResponseEntity.ok(restaurantService.insertRestaurant(RestaurantMapper.toEntity(clientDto)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateRestaurant(@PathVariable("id") Long id, @RequestBody RestaurantDto clientDto) {

        var updateRestaurant = restaurantService.updateRestaurant(RestaurantMapper.toEntity(clientDto, id));

        return Objects.nonNull(updateRestaurant) ? ResponseEntity.ok(updateRestaurant) : ResponseEntity.notFound().build();

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteRestaurant(@PathVariable("id") Long id) {

        return restaurantService.deleteRestaurant(id) ?
            ResponseEntity.ok().build() :
            ResponseEntity.notFound().build();
    }

    @GetMapping("/find/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id) {

        var client = restaurantService.findById(id);

        return client.isPresent() ?
            ResponseEntity.ok(client.get()) :
            ResponseEntity.notFound().build();
    }
}
