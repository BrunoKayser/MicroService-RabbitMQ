package br.com.brunokayser.myfood.cadastro.controller;

import static java.util.Objects.nonNull;

import br.com.brunokayser.myfood.cadastro.dto.MenuInsertDto;
import br.com.brunokayser.myfood.cadastro.mapper.MenuMapper;
import com.br.brunokayser.myfood.cadastro.service.MenuService;
import java.util.Objects;
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
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping("/insert")
    public ResponseEntity insertMenu(@RequestBody MenuInsertDto menuInsertDto) {

        //TODO colocar validação dos dados do insert aqui

        try {
            var menu = menuService.insertMenu(MenuMapper.toDomain(menuInsertDto));

            return nonNull(menu) ?
                ResponseEntity.ok(MenuMapper.toDto(menu)) :
                ResponseEntity.notFound().build();

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateMenu(@PathVariable("id") Long id, @RequestBody MenuInsertDto menuInsertDto) {

        var updateMenu = menuService.updateMenu(MenuMapper.toEntity(menuInsertDto, id));

        return nonNull(updateMenu) ? ResponseEntity.ok(updateMenu) : ResponseEntity.notFound().build();

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMenu(@PathVariable("id") Long id) {

         menuService.deleteMenu(id);

         return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id) {

        var menu  = menuService.findById(id);

        return nonNull(menu)  ? ResponseEntity.ok(menu) : ResponseEntity.notFound().build();
    }

}
