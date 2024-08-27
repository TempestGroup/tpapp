package kz.tempest.tpapp.commons.dtos;

import kz.tempest.tpapp.commons.configs.Response;
import kz.tempest.tpapp.commons.utils.MapperUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class JSONResponse extends HashMap<Object, Object> {

    public static JSONResponse getResponse(String name, Object object) {
        return new JSONResponse() {{
            put(name, object);
        }};
    }

    public static JSONResponse getResponse(Object object) {
        if (object instanceof Collection<?> collection) {
            return getResponse("list", collection);
        } else if (object instanceof Map<?, ?> map) {
            return (JSONResponse) map;
        }
        return map(object);
    }

    private static JSONResponse map(Object object) {
        return (JSONResponse) MapperUtil.mapObjectToMap(object, JSONResponse.class);
    }

}
