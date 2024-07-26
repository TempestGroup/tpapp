package kz.tempest.tpapp.commons.utils;

import kz.tempest.tpapp.commons.contexts.LanguageContext;
import org.apache.tomcat.util.threads.LimitLatch;
import org.springframework.context.MessageSource;
import kz.tempest.tpapp.commons.enums.Language;
import java.util.Locale;

public class TranslateUtil {

    private static MessageSource messageSource;

    public static void setMessageSource(MessageSource messageSource) {
        TranslateUtil.messageSource = messageSource;
    }

    public static String getMessage(String code, Object... arguments) {
        return messageSource.getMessage(code, arguments, new Locale(LanguageContext.getLanguage().name()));
    }

    public static String getSingleMessage(Language language, String code, Object... arguments) {
        return messageSource.getMessage(code, arguments, new Locale(language.name()));
    }

    public static MessageSource getMessageSource() {
        return messageSource;
    }
}
