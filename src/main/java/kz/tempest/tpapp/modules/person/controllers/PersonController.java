package kz.tempest.tpapp.modules.person.controllers;

import kz.tempest.tpapp.commons.annotations.access.AccessChecker;
import kz.tempest.tpapp.commons.contexts.PersonContext;
import kz.tempest.tpapp.commons.dtos.Response;
import kz.tempest.tpapp.commons.dtos.SearchFilter;
import kz.tempest.tpapp.commons.enums.Extension;
import kz.tempest.tpapp.commons.enums.Language;
import kz.tempest.tpapp.commons.enums.Module;
import kz.tempest.tpapp.modules.person.dtos.PersonResponse;
import kz.tempest.tpapp.modules.person.enums.Role;
import kz.tempest.tpapp.modules.person.models.Person;
import kz.tempest.tpapp.modules.person.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/persons")
public class PersonController {
    private final PersonService personService;

    @ResponseBody
    @PostMapping("/")
    @AccessChecker(extensions = { Extension.PERSON_SEARCH })
    public Response getPersons(@RequestHeader(value = "Language", defaultValue = "ru") Language language, @RequestBody SearchFilter searchFilter) {
        return Response.getResponse(personService.getPersons(searchFilter, PersonContext.getCurrentPerson(), language));
    }

    @ResponseBody
    @GetMapping("/{personID}")
    @AccessChecker(roles = { Role.USER })
    public Response getPerson(@PathVariable("personID") Person person, @RequestHeader(value = "Language", defaultValue = "ru") Language language) {
        return Response.getResponse("person", PersonResponse.from(person, language));
    }
}
