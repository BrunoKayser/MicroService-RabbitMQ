package br.com.brunokayser.myfood.cadastro.infraestructure.database;

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
            return MenuMapper.toDomain(menuRepository.save(MenuMapper.toDto(menu)));
        } catch (Exception e) {
            log.error(TAG + "Error in repository trying to save menu");
            throw new InternalErrorException("error.save.menu.repository");
        }
    }

    @Override
    public Optional<Menu> findById(Long id) {
        try {
            return Optional.of(MenuMapper.toDomain(menuRepository.findById(id).get()));
        } catch (Exception e) {
            log.error(TAG + "Error in repository trying find menu");
            throw new InternalErrorException("error.find.menu.repository");
        }
    }

    @Override
    public void delete(Menu menu) {
        try {
        menuRepository.delete(MenuMapper.toDto(menu));
        } catch (Exception e) {
            log.error(TAG + "Error in repository trying delete menu");
            throw new InternalErrorException("error.delete.menu.repository");
        }
    }
}
