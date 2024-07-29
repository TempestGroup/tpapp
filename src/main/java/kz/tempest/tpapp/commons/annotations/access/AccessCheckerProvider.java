package kz.tempest.tpapp.commons.annotations.access;

import kz.tempest.tpapp.commons.contexts.PersonContext;
import kz.tempest.tpapp.commons.enums.Extension;
import kz.tempest.tpapp.commons.enums.Module;
import kz.tempest.tpapp.commons.enums.Right;
import kz.tempest.tpapp.commons.models.ExtensionInfo;
import kz.tempest.tpapp.commons.utils.AccessRightUtil;
import kz.tempest.tpapp.modules.person.enums.Role;
import kz.tempest.tpapp.modules.person.models.Person;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import java.util.Map;

@Aspect
@Component
@RequiredArgsConstructor
public class AccessCheckerProvider {

    @Before(value = "@within(accessChecker) || @annotation(accessChecker)", argNames = "accessChecker")
    public void checkAccess(AccessChecker accessChecker) {
        Person person = PersonContext.getCurrentPerson();
        if (accessChecker != null && person != null) {
            boolean accessRight = checkAccess(accessChecker, person);
            if (!accessRight) {
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
