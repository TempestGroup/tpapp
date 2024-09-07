package kz.tempest.tpapp.commons.contexts;

import kz.tempest.tpapp.commons.enums.Language;
import kz.tempest.tpapp.commons.utils.StringUtil;
import kz.tempest.tpapp.modules.person.models.Person;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

public class PersonContext {

    private static final ThreadLocal<Person> currentPerson = new ThreadLocal<>();

    public static void setPerson(Person person) {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(person, person.getUsername(), person.getRoles()));
        currentPerson.set(person);
    }

    public static boolean isAuthenticated() {
        return !(SecurityContextHolder.getContext().getAuthentication() == null ||
                StringUtil.isEmpty((String)SecurityContextHolder.getContext().getAuthentication().getCredentials()));
    }

    public static Person getCurrentPerson() {
        if (!isAuthenticated()) {
            return null;
        }
        return Person.getPerson(SecurityContextHolder.getContext().getAuthentication());
    }

    public static void clear() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

}
