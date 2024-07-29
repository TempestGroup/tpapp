package kz.tempest.tpapp.commons.annotations.access;

import kz.tempest.tpapp.commons.contexts.PersonContext;
import kz.tempest.tpapp.commons.exceptions.UnauthorizedException;
import kz.tempest.tpapp.commons.utils.AccessRightUtil;
import kz.tempest.tpapp.modules.person.models.Person;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class AccessCheckerProvider {

    @Before(value = "@within(accessChecker) || @annotation(accessChecker)", argNames = "accessChecker")
    public void checkAccess(AccessChecker accessChecker) {
        if (accessChecker != null) {
            if (accessChecker.anonymous()) {
                return;
            }
            Person person = PersonContext.getCurrentPerson();
            if (person == null) {
                throw new UnauthorizedException("Unauthorized!");
            }
            if (!checkAccess(accessChecker, person)) {
                throw new AccessDeniedException("Access denied!");
            }
        }
    }

    private boolean checkAccess(AccessChecker accessChecker, Person person) {
        return AccessRightUtil.checkAccess(person, accessChecker.modules()) ||
                AccessRightUtil.checkAccess(person, accessChecker.extensions()) ||
                person.is(accessChecker.roles());
    }
}
