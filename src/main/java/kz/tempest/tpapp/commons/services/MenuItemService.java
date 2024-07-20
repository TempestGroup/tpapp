package kz.tempest.tpapp.commons.services;

import kz.tempest.tpapp.commons.repositories.MenuItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuItemService {
    private final MenuItemRepository menuItemRepository;


}
