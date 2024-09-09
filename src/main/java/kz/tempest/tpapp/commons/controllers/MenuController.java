package kz.tempest.tpapp.commons.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import kz.tempest.tpapp.commons.annotations.access.AccessChecker;
import kz.tempest.tpapp.commons.contexts.LanguageContext;
import kz.tempest.tpapp.commons.contexts.PersonContext;
import kz.tempest.tpapp.commons.dtos.MenuItemResponse;
import kz.tempest.tpapp.commons.dtos.Response;
import kz.tempest.tpapp.commons.dtos.SearchFilter;
import kz.tempest.tpapp.commons.models.MenuItem;
import kz.tempest.tpapp.commons.services.MenuItemService;
import kz.tempest.tpapp.modules.person.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/menus")
@RequiredArgsConstructor
@Tag(name = "Menu APIs", description = "API for module menu")
public class MenuController {
    private final MenuItemService service;

    @ResponseBody
    @PostMapping("/")
    @AccessChecker(roles = { Role.ADMIN })
    public Response getMenuItems(@RequestBody SearchFilter searchFilter) {
        return Response.getResponse("menus", service.getMenuItems(searchFilter, LanguageContext.getLanguage()));
    }

    @ResponseBody
    @PostMapping("/save")
    @AccessChecker(roles = { Role.ADMIN })
    public Response saveMenuItem(@RequestBody MenuItemResponse menuItemResponse) {
        return Response.getResponse("message", service.saveMenuItem(menuItemResponse));
    }

    @ResponseBody
    @DeleteMapping("/{menuItemID}")
    @AccessChecker(roles = { Role.ADMIN })
    public Response deleteMenuItem(@PathVariable("menuItemID") MenuItem menuItem) {
        return Response.getResponse("message", service.deleteMenuItem(menuItem));
    }

    @ResponseBody
    @GetMapping("/person")
    @AccessChecker(anonymous = false)
    public Response getPersonMenuItems() {
        return Response.getResponse("menus", service.getPersonMenu(PersonContext.getCurrentPerson()));
    }

}
