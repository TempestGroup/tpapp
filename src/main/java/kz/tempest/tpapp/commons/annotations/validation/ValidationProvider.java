package kz.tempest.tpapp.commons.annotations.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kz.tempest.tpapp.commons.contexts.LanguageContext;
import kz.tempest.tpapp.commons.enums.Language;
import kz.tempest.tpapp.commons.utils.ClassUtil;
import kz.tempest.tpapp.commons.utils.TranslateUtil;

import javax.annotation.processing.SupportedAnnotationTypes;
import java.util.regex.Pattern;

@SupportedAnnotationTypes(value = ClassUtil.PACKAGE_PREFIX + ".*")
public class ValidationProvider implements ConstraintValidator<Validation, Object> {

    private String message;
    private String messageKK;
    private String messageRU;
    private String messageEN;
    private boolean isEmail;
    private boolean isNullable;
    private int min;
    private int max;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    @Override
    public void initialize(Validation constraintAnnotation) {
        this.message = constraintAnnotation.message();
        this.messageKK = constraintAnnotation.messageKK();
        this.messageRU = constraintAnnotation.messageRU();
        this.messageEN = constraintAnnotation.messageEN();
        this.isEmail = constraintAnnotation.email();
        this.isNullable = constraintAnnotation.nullable();
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(getErrorMessage())
                    .addConstraintViolation();
            return isNullable;
        }
        if (value instanceof String s) {
            return instanceValuePrimitive(s, constraintValidatorContext);
        }
        return true;
    }

    private String getErrorMessage() {
        if (message == null || message.isEmpty()) {
            return TranslateUtil.getMessage(messageKK, messageRU, messageEN);
        }
        return TranslateUtil.getMessage(message);
    }

    private boolean instanceValuePrimitive(String value, ConstraintValidatorContext constraintValidatorContext) {
        boolean valid = true;
        if (isEmail) {
            valid = EMAIL_PATTERN.matcher(value).matches();
        }
        if (min > 0) {
            valid = valid && value.length() >= min;
        }
        if (max > 0) {
            valid = valid && value.length() <= max;
        }
        if (!valid) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(getErrorMessage())
                    .addConstraintViolation();
        }
        return valid;
    }
}
