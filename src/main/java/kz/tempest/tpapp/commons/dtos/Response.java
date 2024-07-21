package kz.tempest.tpapp.commons.dtos;

import lombok.SneakyThrows;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Response extends HashMap<Object, Object> {

    public static Response getResponse(String name, Object object) {
        return new Response() {{
            put(name, object);
        }};
    }

    public static Response getResponse(Object... objects) {
        return listToMap(Arrays.asList(objects));
    }

    public static Response getResponse(Object object) {
        if (object instanceof Collection<?> collection) {
            return listToMap(collection);
        } else if (object instanceof Map<?, ?> map) {
            return (Response) map;
        }
        return map(object);
    }

    private static Response listToMap(Collection<?> collection) {
        Response response = new Response();
        int index = 0;
        for (Object element : collection) {
            response.put(index, element);
            index++;
        }
        return response;
    }

    @SneakyThrows
    private static Response map(Object object) {
        Response response = new Response();
        Class<?> objectClass = object.getClass();
        while (objectClass != null) {
            for (Field field : objectClass.getDeclaredFields()) {
                field.setAccessible(true);
                response.put(field.getName(), field.get(object));
            }
            objectClass = objectClass.getSuperclass();
        }
        return response;
    }
}
