package kz.tempest.tpapp.commons.dtos;

import lombok.SneakyThrows;
import java.lang.reflect.Field;
import java.util.HashMap;

public class Response extends HashMap<String, Object> {

    public static Response getResponse(String name, Object object) {
        return new Response() {{
            put(name, object);
        }};
    }

    @SneakyThrows
    public static Response getResponse(Object object) {
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
