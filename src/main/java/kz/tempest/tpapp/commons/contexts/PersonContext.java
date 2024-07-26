package kz.tempest.tpapp.commons.contexts;

import kz.tempest.tpapp.modules.person.models.Person;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

public class PersonContext {

    public static void setPerson(Person person) {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(person, person.getUsername(), person.getRoles()));
    }

    public static boolean isAuthenticated() {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            return false;
        }
        if (SecurityContextHolder.getContext().getAuthentication().getCredentials().equals("")) {
            return false;
        }
        return true;
    }

    public static Person getCurrentPerson() {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            return null;
        }
        if (SecurityContextHolder.getContext().getAuthentication().getCredentials().equals("")) {
            return null;
        }
        return Person.getPerson(SecurityContextHolder.getContext().getAuthentication());
    }

    public static void clear() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

}
