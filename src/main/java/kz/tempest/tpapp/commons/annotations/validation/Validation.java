package kz.tempest.tpapp.commons.annotations.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ValidationProvider.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Validation {

    String message() default "";
    String messageKK() default "";
    String messageRU() default "";
    String messageEN() default "";
    boolean email() default false;
    boolean nullable() default true;
    int min() default 0;
    int max() default 0;

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
