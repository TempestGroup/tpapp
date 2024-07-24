package kz.tempest.tpapp.modules.person.controllers;

import jakarta.servlet.http.HttpServletResponse;
import kz.tempest.tpapp.commons.dtos.Response;
import kz.tempest.tpapp.commons.dtos.SearchFilter;
import kz.tempest.tpapp.commons.enums.Language;
import kz.tempest.tpapp.modules.person.dtos.LoginRequest;
import kz.tempest.tpapp.modules.person.dtos.PersonResponse;
import kz.tempest.tpapp.modules.person.models.Person;
import kz.tempest.tpapp.modules.person.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/persons")
public class PersonController {
    private final PersonService personService;

    @ResponseBody
    @PostMapping("/")
    @PreAuthorize("isAuthenticated()")
    public Response getPersons(Authentication auth, @RequestHeader(value = "Language", defaultValue = "ru") Language language, @RequestBody SearchFilter searchFilter) {
        return Response.getResponse(personService.getPersons(searchFilter, Person.getPerson(auth), language));
    }

    @ResponseBody
    @GetMapping("/{personID}")
    @PreAuthorize("isAuthenticated()")
    public Response getPerson(@PathVariable("personID") Person person, @RequestHeader(value = "Language", defaultValue = "ru") Language language) {
        return Response.getResponse("person", PersonResponse.from(person, language));
    }
}
