package kz.tempest.tpapp.commons.dtos;

import kz.tempest.tpapp.commons.utils.MapperUtil;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Response extends HashMap<Object, Object> {

    public static Response getResponse(String name, Object object) {
        return new Response() {{
            put(name, object);
        }};
    }

    public static Response getResponse(Object object) {
        if (object instanceof Collection<?> collection) {
            return getResponse("list", collection);
        } else if (object instanceof Map<?, ?> map) {
            return (Response) map;
        }
        return map(object);
    }

    private static Response map(Object object) {
        return (Response) MapperUtil.mapObjectToMap(object, Response.class);
    }
}
