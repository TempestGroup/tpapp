package kz.tempest.tpapp.modules.person.controllers;

import kz.tempest.tpapp.commons.dtos.Response;
import kz.tempest.tpapp.commons.dtos.SearchFilter;
import kz.tempest.tpapp.commons.enums.Language;
import kz.tempest.tpapp.modules.person.dtos.LoginRequest;
import kz.tempest.tpapp.modules.person.models.Person;
import kz.tempest.tpapp.modules.person.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/persons")
public class PersonController {
    private final PersonService personService;

    @ResponseBody
    @PostMapping("/")
    @PreAuthorize("isAuthenticated()")
    public Response getPersons(Authentication auth, @RequestHeader(value = "Language", defaultValue = "ru") Language language, @RequestBody SearchFilter searchFilter) {
        return Response.getResponse("persons", personService.getPersons(searchFilter, Person.getPerson(auth), language));
    }
}
