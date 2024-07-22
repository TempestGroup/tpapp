package kz.tempest.tpapp.commons.utils;

import kz.tempest.tpapp.commons.enums.Language;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

public class ClassUtil {

    public static Object getLocalizedFieldValue(Class objectClass, Object object, String fieldName, Language language) {
        return getFieldValue(objectClass, object, fieldName + language.suffix());
    }

    public static Object getFieldValue(Class objectClass, Object object, String fieldName) {
        try {
            return Objects.requireNonNull(getField(objectClass, fieldName)).get(object);
        } catch (Exception ignored) {
            return null;
        }
    }

    public static <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception ignored) {
            return null;
        }
    }

    public static <T> T cast(Object obj, Class<T> clazz) {
        try {
            return clazz.cast(obj);
        } catch (Exception ignored) {
            return null;
        }
    }

    public static Field getField(Class objectClass, String fieldName) {
        try {
            Field field = objectClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field;
        } catch (Exception ignored) {
            return null;
        }
    }
}
