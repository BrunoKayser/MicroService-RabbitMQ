package br.com.brunokayser.myfood.cadastro.infraestructure.database;

import static br.com.brunokayser.myfood.cadastro.mapper.MenuMapper.toDto;
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
        try {
            return MenuMapper.toDomain(menuRepository.save(toDto(menu)));

        } catch (Exception e) {
            log.error("Error in repository trying to save, menu: {}, stackTrace{}", menu, e.fillInStackTrace());
            throw new InternalErrorException("error.menu.repository");
        }
    }

    @Override
    public Optional<Menu> findById(Long id) {
        try {
            var menuDto = menuRepository.findById(id);

            if (menuDto.isEmpty()) {
                return Optional.empty();
            }

            return ofNullable(MenuMapper.toDomain(menuDto.get()));
        } catch (Exception e) {
            log.error("Error in repository trying to findById, ID {}, stack-trace", id, e.fillInStackTrace());
            throw new InternalErrorException("error.menu.repository");
        }
    }

    @Override
    public void delete(Menu menu) {
        try {
            menuRepository.delete(toDto(menu));

        } catch (Exception e) {
            log.error("Error in repository trying to delete, menu {}, stack-trace: {}", menu, e.fillInStackTrace());
            throw new InternalErrorException("error.menu.repository");
        }
    }

    public Boolean existsByName(String name) {
        try {
            return menuRepository.existsByName(name);

        } catch (Exception e) {
            log.error("Error in repository trying to verify if exists By name, Name {}, stack-trace", name, e.fillInStackTrace());
            throw new InternalErrorException("error.menu.repository");
        }
    }

    public void update(Menu menu) {
        try {
            menuRepository.update(
                menu.getId(),
                menu.getName(),
                menu.getPrice(),
                menu.getRestaurant().getId());

        } catch (Exception e) {
            log.error("Error in repository trying to update, menu {}, stack-trace", menu, e.fillInStackTrace());
            throw new InternalErrorException("error.menu.repository");
        }
    }
}
