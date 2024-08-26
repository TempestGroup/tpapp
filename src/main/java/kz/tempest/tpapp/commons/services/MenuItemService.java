package kz.tempest.tpapp.commons.services;

import kz.tempest.tpapp.commons.dtos.ResponseMessage;
import kz.tempest.tpapp.commons.enums.Module;
import kz.tempest.tpapp.commons.models.MenuItem;
import kz.tempest.tpapp.commons.repositories.MenuItemRepository;
import kz.tempest.tpapp.modules.person.models.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuItemService {
    private static MenuItemRepository repository;

    @Autowired
    public MenuItemService(MenuItemRepository repository) {
        MenuItemService.repository = repository;
    }

    public void save(MenuItem item) {
        repository.save(item);
    }

    public List<MenuItem> getAll() {
        return repository.findAll();
    }

    public static List<MenuItem> getPersonMenu(Person person) {
        return repository.findAllByModuleIn(Module.getModules(person));
    }
}
