package kz.tempest.tpapp.commons.enums;

import kz.tempest.tpapp.commons.utils.ClassUtil;

import java.util.HashMap;
import java.util.Map;

public enum EventType {
    VIEW("Қарау", "Смотреть", "View"),
    CREATE("Жасау", "Создавать", "Create"),
    UPDATE("Обновлять", "Обновлять", "Update"),
    DELETE("Жою", "Удалить", "Delete");

    private final String nameKK;
    private final String nameRU;
    private final String nameEN;
    private final static Map<String, EventType> typeByCode = new HashMap<>();

    static {
        for (EventType type: values()) {
            typeByCode.put(type.name(), type);
        }
    }

    EventType (
            String nameKK,
            String nameRU,
            String nameEN
    ) {
        this.nameKK = nameKK;
        this.nameRU = nameRU;
        this.nameEN = nameEN;
    }

    public static boolean contains(String code) {
        return typeByCode.containsKey(code);
    }

    public static EventType getByCode(String code) {
        return typeByCode.getOrDefault(code, null);
    }

    public String getName(Language language) {
        return (String) ClassUtil.getLocalizedFieldValue(getClass(), this, "name", language);
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
