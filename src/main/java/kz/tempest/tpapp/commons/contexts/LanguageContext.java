package kz.tempest.tpapp.commons.contexts;

import kz.tempest.tpapp.commons.enums.Language;

public class LanguageContext {

    private static final ThreadLocal<Language> currentLanguage = new ThreadLocal<>();

    public static void setLanguage(Language language) {
        currentLanguage.set(language);
    }

    public static Language getLanguage() {
        return currentLanguage.get();
    }

    public static void clear() {
        currentLanguage.remove();
    }
}
