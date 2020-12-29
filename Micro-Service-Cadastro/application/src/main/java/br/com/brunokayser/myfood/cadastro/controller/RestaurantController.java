package br.com.brunokayser.myfood.cadastro.controller;

import static br.com.brunokayser.myfood.cadastro.mapper.RestaurantMapper.toDomain;
import static br.com.brunokayser.myfood.cadastro.mapper.RestaurantMapper.toDto;

import br.com.brunokayser.myfood.cadastro.dto.RestaurantDto;
import br.com.brunokayser.myfood.cadastro.mapper.RestaurantMapper;
import br.com.brunokayser.myfood.cadastro.validator.RequestValidator;
import com.br.brunokayser.myfood.cadastro.service.RestaurantService;
import java.util.Objects;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurant")
public class RestaurantController {

    @Autowired
    private final RestaurantService restaurantService;

    @Autowired
    private final RequestValidator requestValidator;


    @PostMapping("/insert")
    public ResponseEntity<RestaurantDto> insertRestaurant(@RequestBody @Valid RestaurantDto restaurantDto) {

        requestValidator.validate(restaurantDto);

        var restaurant = (restaurantService.insertRestaurant(toDomain(restaurantDto)));

        return new ResponseEntity<>(toDto(restaurant), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<RestaurantDto> updateRestaurant(@PathVariable("id") Long id, @RequestBody RestaurantDto restaurantDto) {

        requestValidator.validate(restaurantDto);

        var updateRestaurant = restaurantService.updateRestaurant(RestaurantMapper.toEntity(restaurantDto, id));

        return new ResponseEntity<>(toDto(updateRestaurant), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable("id") Long id) {

        restaurantService.deleteRestaurant(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<RestaurantDto> findById(@PathVariable("id") Long id) {

        var restaurant = restaurantService.findById(id);

        return new ResponseEntity<>(toDto(restaurant), HttpStatus.OK);
    }
}
