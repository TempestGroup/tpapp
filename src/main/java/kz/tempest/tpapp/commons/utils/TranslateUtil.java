package kz.tempest.tpapp.commons.utils;

import org.springframework.context.MessageSource;
import kz.tempest.tpapp.commons.enums.Language;
import java.util.Locale;

public class TranslateUtil {

    private static MessageSource messageSource;

    public static void setMessageSource(MessageSource messageSource) {
        TranslateUtil.messageSource = messageSource;
    }

    public static String getMessage(String code, Language language, Object... arguments) {
        return messageSource.getMessage(code, arguments, new Locale(language.name()));
    }
}
