package kz.tempest.tpapp.modules.person.services;

import jakarta.servlet.http.HttpServletRequest;
import kz.tempest.tpapp.commons.constants.CommonMessages;
import kz.tempest.tpapp.commons.contexts.PersonContext;
import kz.tempest.tpapp.commons.dtos.PageObject;
import kz.tempest.tpapp.commons.dtos.ResponseMessage;
import kz.tempest.tpapp.commons.dtos.SearchFilter;
import kz.tempest.tpapp.commons.enums.*;
import kz.tempest.tpapp.commons.enums.Module;
import kz.tempest.tpapp.commons.exceptions.UserExistException;
import kz.tempest.tpapp.commons.utils.EventUtil;
import kz.tempest.tpapp.commons.utils.HttpUtil;
import kz.tempest.tpapp.commons.utils.LogUtil;
import kz.tempest.tpapp.commons.utils.TranslateUtil;
import kz.tempest.tpapp.modules.person.constants.PersonMessages;
import kz.tempest.tpapp.modules.person.dtos.LoginRequest;
import kz.tempest.tpapp.modules.person.dtos.PersonInformationDTO;
import kz.tempest.tpapp.modules.person.dtos.PersonResponse;
import kz.tempest.tpapp.modules.person.dtos.RegisterRequest;
import kz.tempest.tpapp.modules.person.enums.Role;
import kz.tempest.tpapp.modules.person.models.Person;
import kz.tempest.tpapp.modules.person.models.PersonInformation;
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

    public boolean register(RegisterRequest registerRequest, ResponseMessage message, HttpServletRequest request) {
        try {
            if (personRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
                message.set(TranslateUtil.getMessage(PersonMessages.USER_EXIST), RMStatus.ERROR);
                LogUtil.write(new UserExistException());
                return false;
            }
            //emailService.send(ConstantsUtil.getHostName() + "/api/v1/auth/confirm/" + TokenUtil.getRefreshToken(createUserRequest.getUsername()), createUserRequest.getEmail());
            byte[] image = null;
            if (registerRequest.getImage() != null) {
                image = registerRequest.getImage().getBytes();
            }
            Person person = new Person(
                    registerRequest.getEmail(), passwordEncoder.encode(registerRequest.getPassword()),
                    image, List.of(Role.USER), registerRequest.isActive()
            );
            initAccessRights(person);
            person = personRepository.save(person);
            EventUtil.register(Module.PERSON, EventType.CREATE, person.getID(), PersonMessages.REGISTERED_NEW_PERSON, request, person.getUsername(), person.getID());
            message.set(TranslateUtil.getMessage(PersonMessages.SUCCESSFULLY_REGISTERED), RMStatus.SUCCESS);
            return true;
        } catch (Exception e) {
            LogUtil.write(e);
            return false;
        }
    }

    private void initAccessRights(Person person) {
        person.getPersonModuleExtensionRights().put(Extension.PERSON_SEARCH, Right.READ);
        person.getPersonModuleExtensionRights().put(Extension.UPDATE_PERSON_SELF_PROFILE_DATA, Right.WRITE);
    }

    public ResponseMessage savePersonInformation(PersonInformationDTO personInformation, HttpServletRequest request) {
        Person person = PersonContext.getCurrentPerson();
        if (person != null) {
            PersonInformation information = new PersonInformation(personInformation.getNameCyrillic(),
                    personInformation.getNameLatin(), personInformation.getLastnameCyrillic(),
                    personInformation.getLastnameLatin(), null, null ,
                    personInformation.getPhoneNumber());
            person.setInformation(information);
            personRepository.save(person);
            EventUtil.register(Module.PERSON, EventType.UPDATE, person.getID(), PersonMessages.UPDATED_PERSON_INFORMATION, request, person.getUsername(), person.getID());
            return new ResponseMessage(TranslateUtil.getMessage(CommonMessages.SUCCESSFULLY_SAVED), RMStatus.SUCCESS);
        }
        return new ResponseMessage(TranslateUtil.getMessage(CommonMessages.ERROR), RMStatus.ERROR);
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
