package br.com.brunokayser.myfood.cadastro.infraestructure.database;

import static java.util.Optional.ofNullable;

import br.com.brunokayser.myfood.cadastro.infraestructure.database.persistence.MenuRepository;
import br.com.brunokayser.myfood.cadastro.mapper.MenuMapper;
import com.br.brunokayser.myfood.cadastro.domain.Menu;
import com.br.brunokayser.myfood.cadastro.exception.InternalErrorException;
import com.br.brunokayser.myfood.cadastro.port.MenuRepositoryPort;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MenuRepositoryPortImpl implements MenuRepositoryPort {

    private final MenuRepository menuRepository;

    private static final String TAG = "CADASTRO - ";

    @Override
    public Menu save(Menu menu) {
        return MenuMapper.toDomain(menuRepository.save(MenuMapper.toDto(menu)));
    }

    @Override
    public Optional<Menu> findById(Long id) {

        var menuDto = menuRepository.findById(id);

        if (menuDto.isEmpty()) {
            return Optional.empty();
        }

        return ofNullable(MenuMapper.toDomain(menuDto.get()));

    }

    @Override
    public void delete(Menu menu) {
        menuRepository.delete(MenuMapper.toDto(menu));
    }

    public Boolean existsByName(String name) {
        return menuRepository.existsByName(name);
    }

    public void update(Menu menu) {
        menuRepository.update(
            menu.getId(),
            menu.getName(),
            menu.getPrice(),
            menu.getRestaurant().getId());
    }
}
