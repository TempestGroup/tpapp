package kz.tempest.tpapp.modules.person.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import kz.tempest.tpapp.commons.annotations.access.AccessChecker;
import kz.tempest.tpapp.commons.contexts.LanguageContext;
import kz.tempest.tpapp.commons.contexts.PersonContext;
import kz.tempest.tpapp.commons.dtos.Response;
import kz.tempest.tpapp.commons.dtos.ResponseMessage;
import kz.tempest.tpapp.commons.enums.Language;
import kz.tempest.tpapp.commons.enums.RMStatus;
import kz.tempest.tpapp.commons.utils.TranslateUtil;
import kz.tempest.tpapp.modules.person.constants.PersonMessages;
import kz.tempest.tpapp.modules.person.dtos.LoginRequest;
import kz.tempest.tpapp.modules.person.dtos.RegisterRequest;
import kz.tempest.tpapp.modules.person.dtos.TokenResponse;
import kz.tempest.tpapp.modules.person.models.Person;
import kz.tempest.tpapp.modules.person.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Validated
public class AuthController {
    private final PersonService personService;
    private final AuthenticationManager authenticationManager;

    @ResponseBody
    @PostMapping("/login")
    @AccessChecker(anonymous = true)
    public Response login(@Valid @RequestBody LoginRequest loginRequest){
        Response response = new Response();
        Person person = personService.login(loginRequest, authenticationManager);
        PersonContext.setPerson(person);
        TokenResponse token = new TokenResponse(person);
        response.put("message", new ResponseMessage(TranslateUtil.getMessage(PersonMessages.SUCCESSFULLY_LOGIN), RMStatus.SUCCESS));
        response.put("token", token);
        return response;
    }

    @ResponseBody
    @PostMapping(value = "/register", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @AccessChecker(anonymous = true)
    public Response register(HttpServletRequest request, @Valid @ModelAttribute RegisterRequest registerRequest) throws IOException {
        ResponseMessage message = new ResponseMessage();
        if (personService.register(registerRequest, message, request)) {
            return Response.getResponse("message", message);
        }
        return Response.getResponse("message", new ResponseMessage(TranslateUtil
                .getMessage(PersonMessages.SIGN_UP_FAILED), RMStatus.ERROR));
    }

    @GetMapping("/images/{personID}")
    public void getImage(@PathVariable("personID") Person person, HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        response.getOutputStream().write(person.getImage());
    }
}
