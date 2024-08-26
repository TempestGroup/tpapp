package kz.tempest.tpapp.commons.controllers;

import kz.tempest.tpapp.commons.annotations.access.AccessChecker;
import kz.tempest.tpapp.commons.contexts.DeviceContext;
import kz.tempest.tpapp.commons.contexts.LanguageContext;
import kz.tempest.tpapp.commons.dtos.MenuItemResponse;
import kz.tempest.tpapp.commons.dtos.Response;
import kz.tempest.tpapp.commons.enums.Language;
import kz.tempest.tpapp.commons.services.MenuItemService;
import kz.tempest.tpapp.modules.person.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/menus")
@RequiredArgsConstructor
public class MenuController {
    private final MenuItemService service;

    @ResponseBody
    @GetMapping("/")
    @AccessChecker(roles = { Role.ADMIN })
    public Response getMenuItems() {
        return Response.getResponse("menus", MenuItemResponse.from(service.getAll(), DeviceContext.isMobileDevice(), LanguageContext.getLanguage()));
    }


}
