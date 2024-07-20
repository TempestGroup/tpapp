package kz.tempest.tpapp.commons.enums;

import kz.tempest.tpapp.commons.utils.ClassUtil;

import java.util.HashMap;
import java.util.Map;

public enum Module {
    PERSON("person", "Пайдаланушылар", "Пользователи", "Person"),
    DATA("data", "Деректер", "Данные", "Data");

    private final String code;
    private final String nameKK;
    private final String nameRU;
    private final String nameEN;
    private final static Map<String, Module> moduleByCode = new HashMap<>();

    static {
        for (Module module: values()) {
            moduleByCode.put(module.getCode(), module);
        }
    }

    Module(
            String code,
            String nameKK,
            String nameRU,
            String nameEN
    ) {
        this.code = code;
        this.nameKK = nameKK;
        this.nameRU = nameRU;
        this.nameEN = nameEN;
    }

    public String getName(Language language) {
        return (String) ClassUtil.getLocalizedFieldValue(getClass(), this, "name", language);
    }

    public static Module getByCode(String code) {
        return moduleByCode.getOrDefault(code, null);
    }

    public static boolean contains(String code) {
        return moduleByCode.containsKey(code);
    }

    public String getCode() {
        return code;
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
