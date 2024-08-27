package kz.tempest.tpapp.commons.utils;

import kz.tempest.tpapp.commons.configs.Response;
import kz.tempest.tpapp.commons.dtos.JSONResponse;

public class ResponseUtil {
    public static Response getResponse(Object object) {
        return new Response(object);
    }

    public static Response getResponse(String key, Object object) {
        return new Response(JSONResponse.getResponse(key, object));
    }
}
