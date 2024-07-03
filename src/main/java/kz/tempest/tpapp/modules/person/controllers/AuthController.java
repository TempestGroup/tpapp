package kz.tempest.tpapp.modules.person.controllers;

import jakarta.servlet.http.HttpServletRequest;
import kz.tempest.tpapp.commons.dtos.Response;
import kz.tempest.tpapp.commons.dtos.ResponseMessage;
import kz.tempest.tpapp.commons.enums.Language;
import kz.tempest.tpapp.commons.enums.ResponseMessageStatus;
import kz.tempest.tpapp.commons.utils.TranslateUtil;
import kz.tempest.tpapp.modules.person.constants.PersonMessages;
import kz.tempest.tpapp.modules.person.dtos.LoginRequest;
import kz.tempest.tpapp.modules.person.dtos.RegisterRequest;
import kz.tempest.tpapp.modules.person.dtos.TokenResponse;
import kz.tempest.tpapp.modules.person.models.Person;
import kz.tempest.tpapp.modules.person.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final PersonService personService;
    private final AuthenticationManager authenticationManager;

    @ResponseBody
    @PostMapping("/login")
    @PreAuthorize("isAnonymous()")
    public Response login(@RequestBody LoginRequest loginRequest, @RequestHeader(value = "Language", defaultValue = "ru") Language language){
        Response response = new Response();
        Person person = personService.login(loginRequest, authenticationManager);
        TokenResponse token = new TokenResponse(person);
        response.put("message", new ResponseMessage(TranslateUtil
                .getMessage(PersonMessages.SUCCESSFULLY_LOGIN, language), ResponseMessageStatus.SUCCESS));
        response.put("token", token);
        return response;
    }

    @ResponseBody
    @PostMapping("/register")
    @PreAuthorize("isAnonymous()")
    public Response register(HttpServletRequest request, @RequestBody RegisterRequest registerRequest, @RequestHeader(value = "Language", defaultValue = "ru") Language language) throws IOException {
        ResponseMessage message = new ResponseMessage();
        if (personService.register(registerRequest, message, language, request)) {
            return Response.getResponse("message", message);
        }
        return Response.getResponse("message", new ResponseMessage(TranslateUtil
                .getMessage(PersonMessages.SIGN_UP_FAILED, language), ResponseMessageStatus.ERROR));
    }
}
