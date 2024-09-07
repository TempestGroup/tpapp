package kz.tempest.tpapp.modules.person.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import kz.tempest.tpapp.commons.annotations.access.AccessChecker;
import kz.tempest.tpapp.commons.contexts.LanguageContext;
import kz.tempest.tpapp.commons.contexts.PersonContext;
import kz.tempest.tpapp.commons.configs.Response;
import kz.tempest.tpapp.commons.dtos.SearchFilter;
import kz.tempest.tpapp.commons.enums.Extension;
import kz.tempest.tpapp.commons.utils.ResponseUtil;
import kz.tempest.tpapp.modules.person.dtos.PersonInformationDTO;
import kz.tempest.tpapp.modules.person.dtos.PersonResponse;
import kz.tempest.tpapp.modules.person.enums.Role;
import kz.tempest.tpapp.modules.person.models.Person;
import kz.tempest.tpapp.modules.person.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/persons")
@Tag(name = "Person APIs", description = "API for person data")
public class PersonController {
    private final PersonService personService;

    @ResponseBody
    @PostMapping("/")
    @AccessChecker(extensions = { Extension.PERSON_SEARCH })
    public Response getPersons(@RequestBody SearchFilter searchFilter) {
        return ResponseUtil.getResponse(personService.getPersons(searchFilter, PersonContext.getCurrentPerson(), LanguageContext.getLanguage()));
    }

    @ResponseBody
    @PostMapping("/save-person-information")
    @AccessChecker(anonymous = false)
    public Response savePersonInformation(@RequestBody PersonInformationDTO information, HttpServletRequest request) {
        return ResponseUtil.getResponse("message", personService.savePersonInformation(information, request));
    }

    @ResponseBody
    @GetMapping("/get-person-information")
    @AccessChecker(anonymous = false)
    public Response getPersonInformation() {
        return ResponseUtil.getResponse("information", PersonInformationDTO.from(Objects.requireNonNull(PersonContext.getCurrentPerson())));
    }

    @ResponseBody
    @GetMapping("/{personID}")
    @AccessChecker(roles = { Role.USER })
    public Response getPerson(@PathVariable("personID") Person person) {
        return ResponseUtil.getResponse("person", PersonResponse.from(person, LanguageContext.getLanguage()));
    }
}
