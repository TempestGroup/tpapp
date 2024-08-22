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

    public static String getMessage(String messageKK, String messageRU, String messageEN) {
        return getMessage(messageKK, messageRU, messageEN, LanguageContext.getLanguage());
    }

    public static String getMessage(String messageKK, String messageRU, String messageEN, Language language) {
        return switch (language) {
            case kk -> messageKK;
            case ru -> messageRU;
            case en -> messageEN;
            default -> getMessage(messageKK, messageRU, messageEN, Language.valueOf(Locale.getDefault().getLanguage()));
        };
    }

    public static MessageSource getMessageSource() {
        return messageSource;
    }
}
