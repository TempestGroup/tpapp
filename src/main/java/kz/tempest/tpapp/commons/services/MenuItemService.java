package kz.tempest.tpapp.commons.services;

import kz.tempest.tpapp.commons.constants.CommonMessages;
import kz.tempest.tpapp.commons.dtos.*;
import kz.tempest.tpapp.commons.enums.Language;
import kz.tempest.tpapp.commons.enums.Module;
import kz.tempest.tpapp.commons.enums.RMStatus;
import kz.tempest.tpapp.commons.models.MenuItem;
import kz.tempest.tpapp.commons.repositories.MenuItemRepository;
import kz.tempest.tpapp.commons.specifications.MenuItemSpecification;
import kz.tempest.tpapp.commons.utils.TranslateUtil;
import kz.tempest.tpapp.modules.person.models.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuItemService {
    private MenuItemRepository repository;

    @Autowired
    public MenuItemService(MenuItemRepository repository) {
        this.repository = repository;
    }

    public PageObject<MenuItemResponse> getMenuItems(SearchFilter searchFilter, Language language) {
        return new PageObject<>(repository.findAll(MenuItemSpecification.getSpecification(searchFilter.getFilter()),
                        searchFilter.getPageable(MenuItem.class, language))
                .map(menuItem -> MenuItemResponse.from(menuItem, language)));
    }

    public ResponseMessage saveMenuItem(MenuItemResponse menuItemResponse) {
        repository.save(toMenuItem(menuItemResponse));
        return new ResponseMessage(TranslateUtil.getMessage(CommonMessages.SUCCESSFULLY_SAVED), RMStatus.SUCCESS);
    }

    public ResponseMessage deleteMenuItem(MenuItem menuItem) {
        repository.delete(menuItem);
        return new ResponseMessage(TranslateUtil.getMessage(CommonMessages.SUCCESSFULLY_DELETED), RMStatus.SUCCESS);
    }

    public List<MenuItem> getPersonMenu(Person person) {
        return repository.findAllByModuleIn(Module.getModules(person));
    }

    public static MenuItem toMenuItem(MenuItemResponse dto) {
        return new MenuItem(dto.getId(), dto.getNameKK(), dto.getNameRU(), dto.getNameEN(),
                dto.getLink(), dto.getModule(), dto.getMobileIcon(), dto.getWebIcon());
    }
}
