package kz.tempest.tpapp.commons.annotations.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kz.tempest.tpapp.commons.utils.TranslateUtil;
import java.util.regex.Pattern;

public class ValidationProvider implements ConstraintValidator<Validation, Object> {

    private String message;
    private String messageKK;
    private String messageRU;
    private String messageEN;
    private boolean isPhoneNumber;
    private boolean isEmail;
    private boolean isNullable;
    private int min;
    private int max;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^(\\+\\d{1,3}( )?)?\\d{10}$");

    @Override
    public void initialize(Validation constraintAnnotation) {
        this.message = constraintAnnotation.message();
        this.messageKK = constraintAnnotation.messageKK();
        this.messageRU = constraintAnnotation.messageRU();
        this.messageEN = constraintAnnotation.messageEN();
        this.isEmail = constraintAnnotation.email();
        this.isPhoneNumber = constraintAnnotation.phone();
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
        if (isPhoneNumber) {
            valid = PHONE_NUMBER_PATTERN.matcher(value).matches();
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
