package kz.tempest.tpapp.commons.enums;

import kz.tempest.tpapp.commons.utils.ClassUtil;

import java.util.HashMap;
import java.util.Map;

public enum Language {
    kk("Қазақша", "Казахский", "Kazakh"),
    ru("Орысша", "Русский", "Russian"),
    en("Ағылшынша", "Английский", "English");

    private final String nameKK;
    private final String nameRU;
    private final String nameEN;
    private final static Map<String, Language> languagesByCode = new HashMap<>();

    static {
        for (Language language: values()) {
            languagesByCode.put(language.name(), language);
        }
    }

    Language (
            String nameKK,
            String nameRU,
            String nameEN
    ) {
        this.nameKK = nameKK;
        this.nameRU = nameRU;
        this.nameEN = nameEN;
    }

    public String getName(Language language) {
        return (String) ClassUtil.getLocalizedFieldValue(getClass(), this, "name", language);
    }

    public static boolean contains(String language) {
        return languagesByCode.containsKey(language);
    }

    public String suffix() {
        return this.name().toUpperCase();
    }

    public String getNameKK() {
        return nameKK;
    }

    public String getNameRU() {
        return nameRU;
    }

    public String getNameEN() {
        return nameEN;
    }
}
