package kz.tempest.tpapp.commons.utils;

import kz.tempest.tpapp.commons.enums.Language;
import lombok.SneakyThrows;

import java.lang.reflect.Field;

public class ClassUtil {

    public static Object getLocalizedFieldValue(Class objectClass, Object object, String fieldName, Language language) {
        return getFieldValue(objectClass, object, fieldName + language.suffix());
    }

    @SneakyThrows
    public static Object getFieldValue(Class objectClass, Object object, String fieldName) {
        Field field = objectClass.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(object);
    }
}
