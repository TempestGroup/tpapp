package kz.tempest.tpapp.commons.utils;

import kz.tempest.tpapp.commons.fileReader.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;

public class MapperUtil {
    public static <T> T mapToObject(Object source, Class<T> targetClass) {
        try {
            T target = targetClass.newInstance();
            Class<?> sourceClass = source.getClass();
            for (Field sourceField : sourceClass.getDeclaredFields()) {
                sourceField.setAccessible(true);
                try {
                    Object value = sourceField.get(source);
                    Field targetField = targetClass.getDeclaredField(sourceField.getName());
                    targetField.setAccessible(true);
                    targetField.set(target, value);
                } catch (Exception ignored) {}
            }
            return target;
        } catch (Exception e) {
            LogUtil.write(e);
            return null;
        }
    }

    public static Map<Object, Object> mapToMap(Object object, Class<? extends Map<Object, Object>> clazz) {
        try {
            Map<Object, Object> map = clazz.getDeclaredConstructor().newInstance();
            Class<?> objectClass = object.getClass();
            while (objectClass != null) {
                for (Field field : objectClass.getDeclaredFields()) {
                    field.setAccessible(true);
                    map.put(field.getName(), field.get(object));
                }
                objectClass = objectClass.getSuperclass();
            }
            return map;
        } catch (Exception e) {
            LogUtil.write(e);
            return null;
        }
    }

    public static <T> T mapToObject(Map<String, Object> map, Class<T> clazz) {
        try {
            T obj = clazz.getDeclaredConstructor().newInstance();
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                String fieldName = field.getName();
                Object value = map.get(fieldName);
                if (value != null) {
                    Class<?> fieldType = field.getType();
                    if (fieldType.isPrimitive()) {
                        field.set(obj, convertValue(value, fieldType));
                    } else if (List.class.isAssignableFrom(fieldType)) {
                        ParameterizedType genericType = (ParameterizedType) field.getGenericType();
                        Class<?> elementType = (Class<?>) genericType.getActualTypeArguments()[0];
                        List<Object> list = new ArrayList<>();
                        for (Object item : (List<?>) value) {
                            list.add(convertValue(item, elementType));
                        }
                        field.set(obj, list);
                    } else if (Map.class.isAssignableFrom(fieldType)) {
                        ParameterizedType genericType = (ParameterizedType) field.getGenericType();
                        Class<?> keyType = (Class<?>) genericType.getActualTypeArguments()[0];
                        Class<?> valueType = (Class<?>) genericType.getActualTypeArguments()[1];
                        Map<Object, Object> mapResult = new HashMap<>();
                        for (Map.Entry<?, ?> entry : ((Map<?, ?>) value).entrySet()) {
                            Object key = convertValue(entry.getKey(), keyType);
                            Object mapValue = convertValue(entry.getValue(), valueType);
                            mapResult.put(key, mapValue);
                        }
                        field.set(obj, mapResult);
                    } else if (isComplexType(fieldType)) {
                        Object nestedObject = mapToObject((Map<String, Object>) value, fieldType);
                        field.set(obj, nestedObject);
                    }
                }
            }
            return obj;
        } catch (Exception e) {
            LogUtil.write(e);
            return null;
        }
    }

    public static <T> T mapToObject(String json, Class<T> targetClass) {
        return mapToObject(Reader.getInstance(".json", json.getBytes()).read(), targetClass);
    }

    private static Object convertValue(Object value, Class<?> targetType) {
        if (value == null) {
            return null;
        }
        if (targetType == String.class) {
            return value.toString();
        } else if (targetType == int.class || targetType == Integer.class) {
            return Integer.parseInt(value.toString());
        } else if (targetType == double.class || targetType == Double.class) {
            return Double.parseDouble(value.toString());
        } else if (targetType == boolean.class || targetType == Boolean.class) {
            return Boolean.parseBoolean(value.toString());
        } else if (targetType == long.class || targetType == Long.class) {
            return Long.parseLong(value.toString());
        } else if (targetType == float.class || targetType == Float.class) {
            return Float.parseFloat(value.toString());
        } else if (targetType == short.class || targetType == Short.class) {
            return Short.parseShort(value.toString());
        } else if (targetType == byte.class || targetType == Byte.class) {
            return Byte.parseByte(value.toString());
        }
        return value;
    }

    private static boolean isComplexType(Class<?> type) {
        return !type.isPrimitive() && !type.isAssignableFrom(String.class) && !List.class.isAssignableFrom(type) && !Map.class.isAssignableFrom(type);
    }
}
