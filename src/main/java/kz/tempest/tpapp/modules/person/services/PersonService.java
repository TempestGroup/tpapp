package kz.tempest.tpapp.modules.person.services;

import kz.tempest.tpapp.commons.dtos.PageObject;
import kz.tempest.tpapp.commons.dtos.ResponseMessage;
import kz.tempest.tpapp.commons.dtos.SearchFilter;
import kz.tempest.tpapp.commons.enums.Language;
import kz.tempest.tpapp.commons.exceptions.UserExistException;
import kz.tempest.tpapp.commons.utils.LogUtil;
import kz.tempest.tpapp.commons.utils.TokenUtil;
import kz.tempest.tpapp.commons.utils.TranslateUtil;
import kz.tempest.tpapp.modules.person.constants.PersonMessages;
import kz.tempest.tpapp.modules.person.dtos.LoginRequest;
import kz.tempest.tpapp.modules.person.dtos.PersonResponse;
import kz.tempest.tpapp.modules.person.dtos.RegisterRequest;
import kz.tempest.tpapp.modules.person.models.Person;
import kz.tempest.tpapp.modules.person.repositories.PersonRepository;
import kz.tempest.tpapp.modules.person.specifications.PersonSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PersonService implements UserDetailsService {
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    public Person login(LoginRequest loginRequest, AuthenticationManager authManager) {
        return Person.getPerson(authManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())));
    }

    public boolean register(RegisterRequest registerRequest, ResponseMessage message, Language language) throws IOException {
        if (personRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            message.setContent(TranslateUtil.getMessage(PersonMessages.USER_EXIST, language));
            LogUtil.write(new UserExistException());
            return false;
        }
        //emailService.send(ConstantsUtil.getHostName() + "/api/v1/auth/confirm/" + TokenUtil.getRefreshToken(createUserRequest.getUsername()), createUserRequest.getEmail());
        byte[] image = null;
        if (registerRequest.getImage() != null) {
            image = registerRequest.getImage().getBytes();
        }
        personRepository.save(
            new Person(
                registerRequest.getEmail(),
                passwordEncoder.encode(registerRequest.getPassword()),
                image,
                registerRequest.isActive()
            )
        );
        return true;
    }

    @Override
    public Person loadUserByUsername(String username) {
        try {
            return personRepository.findByEmail(username).orElse(null);
        } catch (UsernameNotFoundException e) {
            LogUtil.write(e);
            return null;
        }
    }

    public PageObject<PersonResponse> getPersons(SearchFilter filter, Person person, Language language) {
        return new PageObject<>(personRepository.findAll(PersonSpecification
                .getSpecification(person, filter.getFilter()), filter.getPageable(Person.class, language))
                .map(pf -> PersonResponse.from(pf, language)));
    }
}
