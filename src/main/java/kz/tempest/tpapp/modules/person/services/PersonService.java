package kz.tempest.tpapp.modules.person.services;

import kz.tempest.tpapp.commons.constants.ErrorMessages;
import kz.tempest.tpapp.commons.dtos.ResponseMessage;
import kz.tempest.tpapp.commons.enums.Language;
import kz.tempest.tpapp.commons.exceptions.UserExistException;
import kz.tempest.tpapp.commons.utils.LogUtil;
import kz.tempest.tpapp.commons.utils.TranslateUtil;
import kz.tempest.tpapp.modules.person.dtos.PersonRequest;
import kz.tempest.tpapp.modules.person.models.Person;
import kz.tempest.tpapp.modules.person.repositories.PersonRepository;
import lombok.RequiredArgsConstructor;
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

    public boolean register(PersonRequest personRequest, ResponseMessage message, Language language) throws IOException {
        if (personRepository.findByEmail(personRequest.getEmail()).isPresent()) {
            message.setContent(TranslateUtil.getMessage(ErrorMessages.USER_EXIST, language));
            LogUtil.write(new UserExistException());
            return false;
        }
        //emailService.send(ConstantsUtil.getHostName() + "/api/v1/auth/confirm/" + TokenUtil.getRefreshToken(createUserRequest.getUsername()), createUserRequest.getEmail());
        byte[] image = null;
        if (personRequest.getImage() != null) {
            image = personRequest.getImage().getBytes();
        }
        personRepository.save(
            new Person(
                personRequest.getEmail(),
                passwordEncoder.encode(personRequest.getPassword()),
                image,
                personRequest.isActive()
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
}
