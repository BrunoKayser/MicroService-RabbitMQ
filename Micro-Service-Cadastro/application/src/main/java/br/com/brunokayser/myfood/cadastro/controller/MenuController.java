package br.com.brunokayser.myfood.cadastro.controller;

import br.com.brunokayser.myfood.cadastro.dto.MenuInsertDto;
import br.com.brunokayser.myfood.cadastro.mapper.MenuMapper;
import com.br.brunokayser.myfood.cadastro.service.MenuService;
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

        try {
            var menu = menuService.insertMenu(MenuMapper.toDomain(menuInsertDto));

            return Objects.nonNull(menu) ?
                ResponseEntity.ok(MenuMapper.toDto(menu)) :
                ResponseEntity.notFound().build();

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateMenu(@PathVariable("id") Long id, @RequestBody MenuInsertDto menuInsertDto) {

        var updateMenu = menuService.updateMenu(MenuMapper.toEntity(menuInsertDto, id));

        return Objects.nonNull(updateMenu) ? ResponseEntity.ok(updateMenu) : ResponseEntity.notFound().build();

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteMenu(@PathVariable("id") Long id) {

        return menuService.deleteMenu(id) ?
            ResponseEntity.ok().build() :
            ResponseEntity.notFound().build();
    }

    @GetMapping("/find/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id) {

        var client = menuService.findById(id);

        return client.isPresent() ?
            ResponseEntity.ok(client.get()) :
            ResponseEntity.notFound().build();
    }

}
