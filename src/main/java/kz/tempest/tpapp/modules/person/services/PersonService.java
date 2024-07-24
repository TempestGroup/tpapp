package kz.tempest.tpapp.modules.person.services;

import jakarta.servlet.http.HttpServletRequest;
import kz.tempest.tpapp.commons.dtos.PageObject;
import kz.tempest.tpapp.commons.dtos.ResponseMessage;
import kz.tempest.tpapp.commons.dtos.SearchFilter;
import kz.tempest.tpapp.commons.enums.EventType;
import kz.tempest.tpapp.commons.enums.Language;
import kz.tempest.tpapp.commons.enums.Module;
import kz.tempest.tpapp.commons.enums.RMStatus;
import kz.tempest.tpapp.commons.exceptions.UserExistException;
import kz.tempest.tpapp.commons.utils.EventUtil;
import kz.tempest.tpapp.commons.utils.LogUtil;
import kz.tempest.tpapp.commons.utils.TranslateUtil;
import kz.tempest.tpapp.modules.person.constants.PersonMessages;
import kz.tempest.tpapp.modules.person.dtos.LoginRequest;
import kz.tempest.tpapp.modules.person.dtos.PersonResponse;
import kz.tempest.tpapp.modules.person.dtos.RegisterRequest;
import kz.tempest.tpapp.modules.person.enums.Role;
import kz.tempest.tpapp.modules.person.models.Person;
import kz.tempest.tpapp.modules.person.repositories.PersonRepository;
import kz.tempest.tpapp.modules.person.specifications.PersonSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService implements UserDetailsService {
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    public Person login(LoginRequest loginRequest, AuthenticationManager authManager) {
        return Person.getPerson(authManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())));
    }

    public boolean register(RegisterRequest registerRequest, ResponseMessage message, Language language, HttpServletRequest request) throws IOException {
        if (personRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            message.setContent(TranslateUtil.getMessage(PersonMessages.USER_EXIST, language));
            message.setStatus(RMStatus.ERROR);
            LogUtil.write(new UserExistException());
            return false;
        }
        //emailService.send(ConstantsUtil.getHostName() + "/api/v1/auth/confirm/" + TokenUtil.getRefreshToken(createUserRequest.getUsername()), createUserRequest.getEmail());
        byte[] image = null;
        if (registerRequest.getImage() != null) {
            image = registerRequest.getImage().getBytes();
        }
        Person person = personRepository.save(new Person(
            registerRequest.getEmail(), passwordEncoder.encode(registerRequest.getPassword()),
            image, List.of(Role.USER, Role.EMPLOYEE), registerRequest.isActive()
        ));
        EventUtil.register(Module.PERSON, EventType.CREATE, person.getID(), request, PersonMessages.REGISTERED_NEW_PERSON, person.getUsername(), person.getID());
        message.setContent(TranslateUtil.getMessage(PersonMessages.SUCCESSFULLY_REGISTERED, language));
        message.setStatus(RMStatus.SUCCESS);
        return true;
    }

    @Override
    public Person loadUserByUsername(String username) {
        return personRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Not found user with '" + username + "'!"));
    }

    public PageObject<PersonResponse> getPersons(SearchFilter filter, Person person, Language language) {
        return new PageObject<>(personRepository.findAll(PersonSpecification
                .getSpecification(person, filter.getFilter()), filter.getPageable(Person.class, language))
                .map(p -> PersonResponse.from(p, language)));
    }
}
