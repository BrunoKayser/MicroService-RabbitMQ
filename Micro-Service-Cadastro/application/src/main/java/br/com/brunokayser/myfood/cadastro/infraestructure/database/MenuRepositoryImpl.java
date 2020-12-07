package br.com.brunokayser.myfood.cadastro.infraestructure.database;

import br.com.brunokayser.myfood.cadastro.infraestructure.database.persistence.MenuRepository;
import br.com.brunokayser.myfood.cadastro.mapper.MenuMapper;
import com.br.brunokayser.myfood.cadastro.domain.Menu;
import com.br.brunokayser.myfood.cadastro.port.MenuRepositoryPort;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MenuRepositoryImpl implements MenuRepositoryPort {

    private final MenuRepository menuRepository;

    @Override
    public Menu save(Menu menu) {
        return MenuMapper.toDomain(menuRepository.save(MenuMapper.toDto(menu)));
    }

    @Override
    public Optional<Menu> findById(Long id) {
        return Optional.of(MenuMapper.toDomain(menuRepository.findById(id).get()));
    }

    @Override
    public void delete(Menu menu) {
        menuRepository.delete(MenuMapper.toDto(menu));
    }
}
