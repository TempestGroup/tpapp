package kz.tempest.tpapp.commons.utils;

import org.json.JSONObject;
import org.json.JSONArray;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

public class MapperUtil {
    public static <T> T mapObjectToObject(Object source, Class<T> targetClass) {
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

    public static Map<Object, Object> mapObjectToMap(Object object, Class<? extends Map<Object, Object>> clazz) {
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

    public static <T> T mapMapToObject(Map<String, Object> map, Class<T> clazz) {
        try {
            T obj = clazz.newInstance();
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
                            if (item != null) {
                                list.add(convertValue(item, elementType));
                            }
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
                        Object nestedObject = mapMapToObject((Map<String, Object>) value, fieldType);
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

    private static List<Object> mapJsonArrayToList(JSONArray jsonArray, Type type) throws ClassNotFoundException {
        List<Object> list = new ArrayList<>();
        Class<?> elementType = Object.class;

        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            if (actualTypeArguments.length > 0) {
                elementType = Class.forName(actualTypeArguments[0].getTypeName());
            }
        }

        for (int i = 0; i < jsonArray.length(); i++) {
            Object item = jsonArray.get(i);
            if (item instanceof JSONObject) {
                item = mapJsonToObject(item.toString(), elementType);
            } else if (item instanceof JSONArray) {
                item = mapJsonArrayToList((JSONArray) item, elementType);
            }
            list.add(item);
        }
        return list;
    }

    private static Map<String, Object> mapJsonObjectToMap(JSONObject jsonObject, Type type) throws ClassNotFoundException {
        Map<String, Object> map = new HashMap<>();
        Class<?> keyType = String.class;
        Class<?> valueType = Object.class;

        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            if (actualTypeArguments.length > 1) {
                keyType = Class.forName(actualTypeArguments[0].getTypeName());
                valueType = Class.forName(actualTypeArguments[1].getTypeName());
            }
        }

        for (String key : jsonObject.keySet()) {
            Object value = jsonObject.get(key);
            if (value instanceof JSONObject) {
                value = mapJsonToObject(value.toString(), valueType);
            } else if (value instanceof JSONArray) {
                value = mapJsonArrayToList((JSONArray) value, valueType);
            }
            map.put(key, value);
        }
        return map;
    }

    public static <T> T mapJsonToObject(String jsonString, Class<T> clazz) {
        try {
            T obj = clazz.newInstance();
            JSONObject jsonObject = new JSONObject(jsonString);
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                String fieldName = field.getName();
                if (jsonObject.has(fieldName)) {
                    Object value = jsonObject.get(fieldName);
                    if (field.getType().isPrimitive() || field.getType().equals(String.class) || field.getType().equals(Integer.class)) {
                        field.set(obj, convertValue(value, field.getType()));
                    } else if (List.class.isAssignableFrom(field.getType())) {
                        field.set(obj, mapJsonArrayToList((JSONArray) value, field.getGenericType()));
                    } else if (Map.class.isAssignableFrom(field.getType())) {
                        field.set(obj, mapJsonObjectToMap((JSONObject) value, field.getGenericType()));
                    } else if (isComplexType(field.getType())) {
                        field.set(obj, mapJsonToObject(value.toString(), field.getType()));
                    }
                }
            }
            return obj;
        } catch (Exception e) {
            LogUtil.write(e);
            return null;
        }
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

    public static <T> Map<Object, T> mapListToMap(List<T> list, Class<? extends Map<Object, T>> clazz, String keyMethodName) {
        try {
            Map<Object, T> map = clazz.newInstance();
            for (T item : list) {
                Method keyMethod = item.getClass().getMethod(keyMethodName);
                Object key = keyMethod.invoke(item);
                map.put(key, item);
            }
            return map;
        } catch (Exception e) {
            LogUtil.write(e);
            return Collections.emptyMap();
        }
    }

    public static <V> List<V> mapMapToList(Map<Object, V> map, Comparator<? super Object> keyComparator) {
        List<Map.Entry<Object, V>> entryList = new ArrayList<>(map.entrySet());
        entryList.sort((e1, e2) -> keyComparator.compare(e1.getKey(), e2.getKey()));
        List<V> valueList = new ArrayList<>();
        for (Map.Entry<Object, V> entry : entryList) {
            valueList.add(entry.getValue());
        }
        return valueList;
    }
}
