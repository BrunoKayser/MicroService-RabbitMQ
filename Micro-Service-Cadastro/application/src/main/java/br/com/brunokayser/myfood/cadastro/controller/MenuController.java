package br.com.brunokayser.myfood.cadastro.controller;

import static br.com.brunokayser.myfood.cadastro.mapper.MenuMapper.toDomain;
import static br.com.brunokayser.myfood.cadastro.mapper.MenuMapper.toDto;
import static java.util.Objects.nonNull;

import br.com.brunokayser.myfood.cadastro.dto.MenuDto;
import br.com.brunokayser.myfood.cadastro.dto.MenuInsertDto;
import br.com.brunokayser.myfood.cadastro.dto.MenuUpdateDto;
import br.com.brunokayser.myfood.cadastro.mapper.MenuMapper;
import br.com.brunokayser.myfood.cadastro.validator.RequestValidator;
import com.br.brunokayser.myfood.cadastro.service.MenuService;
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
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private final MenuService menuService;

    @Autowired
    private final RequestValidator requestValidator;

    @PostMapping("/insert")
    public ResponseEntity<MenuDto> insertMenu(@RequestBody @Valid MenuInsertDto menuInsertDto) {

        requestValidator.validate(menuInsertDto);

        var menu = menuService.insertMenu(toDomain(menuInsertDto));

        return new ResponseEntity<>(toDto(menu), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MenuDto> updateMenu(@PathVariable("id") Long id, @RequestBody MenuUpdateDto menuUpdateDto) {

        requestValidator.validate(menuUpdateDto);

        var updateMenu = menuService.updateMenu(toDomain(menuUpdateDto, id));

        return new ResponseEntity<>(toDto(updateMenu), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMenu(@PathVariable("id") Long id) {

        menuService.deleteMenu(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<MenuDto> findById(@PathVariable("id") Long id) {

        var menu = menuService.findById(id);

        return new ResponseEntity<>(toDto(menu), HttpStatus.OK);
    }
}
