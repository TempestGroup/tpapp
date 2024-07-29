package kz.tempest.tpapp.commons.annotations.access;

import kz.tempest.tpapp.commons.enums.Extension;
import kz.tempest.tpapp.commons.enums.Module;
import kz.tempest.tpapp.modules.person.enums.Role;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessChecker {
    Module[] modules() default {};
    Extension[] extensions() default {};
    Role[] roles() default {};

}
