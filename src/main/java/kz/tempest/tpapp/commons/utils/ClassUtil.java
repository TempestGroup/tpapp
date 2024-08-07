package kz.tempest.tpapp.commons.utils;

import jakarta.persistence.Embedded;
import kz.tempest.tpapp.commons.enums.Language;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class ClassUtil {

    public static final String PACKAGE_PREFIX = "kz.tempest.tpapp";

    public static Object getLocalizedFieldValue(Class objectClass, Object object, String fieldName, Language language) {
        return getFieldValue(objectClass, object, fieldName + language.suffix());
    }

    public static String getPackage(Class<?> clazz) {
        Package p = clazz.getPackage();
        return (clazz.getPackage() == null) ? "" : clazz.getPackage().getName();
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

    private static boolean hasField(Class<?> clazz, String name, Set<Class<?>> visited) {
        if (clazz == null || visited.contains(clazz) || clazz == Object.class) {
            return false;
        }
        visited.add(clazz);
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getName().equals(name)) {
                return true;
            }
            if (!field.isAnnotationPresent(Embedded.class)) {
                continue;
            }
            if (hasField(field.getType(), name, visited)) {
                return true;
            }
        }
        return hasField(clazz.getSuperclass(), name, visited);
    }

    private static String getFieldName(Class<?> clazz, String name, Set<Class<?>> visited) {
        if (clazz == null || visited.contains(clazz) || clazz == Object.class) {
            return "";
        }
        visited.add(clazz);
        for(Field field : clazz.getDeclaredFields()) {
            if (field.getName().equals(name)) {
                return field.getName();
            }
            if (!field.isAnnotationPresent(Embedded.class)) {
                continue;
            }
            return field.getName() + "." + getFieldName(field.getType(), name, visited);
        }
        if (clazz.getSuperclass() == Objects.class) {
            return "";
        }
        return getFieldName(clazz.getSuperclass(), name, visited);
    }

    public static String getFieldName(Class<?> clazz, String name) {
        return getFieldName(clazz, name, new HashSet<>());
    }

    public static String getLocalizedFieldName(Class<?> clazz, String name, Language language) {
        return getFieldName(clazz, name + language.suffix());
    }

    public static boolean hasField(Class<?> clazz, String name) {
        return hasField(clazz, name, new HashSet<>());
    }

    public static boolean hasLocalizedField(Class<?> clazz, String name, Language language) {
        return hasField(clazz, name + language.suffix());
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
