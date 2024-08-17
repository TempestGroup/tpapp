package kz.tempest.tpapp.modules.person.controllers;

import jakarta.servlet.http.HttpServletRequest;
import kz.tempest.tpapp.commons.annotations.access.AccessChecker;
import kz.tempest.tpapp.commons.contexts.LanguageContext;
import kz.tempest.tpapp.commons.contexts.PersonContext;
import kz.tempest.tpapp.commons.dtos.Response;
import kz.tempest.tpapp.commons.dtos.SearchFilter;
import kz.tempest.tpapp.commons.enums.Extension;
import kz.tempest.tpapp.commons.enums.Language;
import kz.tempest.tpapp.modules.person.dtos.PersonInformationDTO;
import kz.tempest.tpapp.modules.person.dtos.PersonResponse;
import kz.tempest.tpapp.modules.person.enums.Role;
import kz.tempest.tpapp.modules.person.models.Person;
import kz.tempest.tpapp.modules.person.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/persons")
public class PersonController {
    private final PersonService personService;

    @ResponseBody
    @PostMapping("/")
    @AccessChecker(extensions = { Extension.PERSON_SEARCH })
    public Response getPersons(@RequestBody SearchFilter searchFilter) {
        return Response.getResponse(personService.getPersons(searchFilter, PersonContext.getCurrentPerson(), LanguageContext.getLanguage()));
    }

    @ResponseBody
    @PostMapping("/save-person-information")
    @AccessChecker(anonymous = false)
    public Response savePersonInformation(@RequestBody PersonInformationDTO information, HttpServletRequest request) {
        return Response.getResponse("message", personService.savePersonInformation(information, request));
    }

    @ResponseBody
    @GetMapping("/get-person-information")
    @AccessChecker(anonymous = false)
    public Response getPersonInformation() {
        return Response.getResponse("information", PersonInformationDTO.from(PersonContext.getCurrentPerson()));
    }


    @ResponseBody
    @GetMapping("/{personID}")
    @AccessChecker(roles = { Role.USER })
    public Response getPerson(@PathVariable("personID") Person person) {
        return Response.getResponse("person", PersonResponse.from(person, LanguageContext.getLanguage()));
    }
}
