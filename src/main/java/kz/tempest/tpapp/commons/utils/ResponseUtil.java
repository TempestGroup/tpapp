package kz.tempest.tpapp.commons.utils;

import kz.tempest.tpapp.commons.configs.Response;
import kz.tempest.tpapp.commons.dtos.JSONResponse;
import org.springframework.http.HttpStatus;

public class ResponseUtil {
    public static Response getResponse(Object object) {
        return new Response(object);
    }

    public static Response getResponse(Object object, HttpStatus status){
        return new Response(object, status);
    }

    public static Response getResponse(String key, Object object) {
        return getResponse(JSONResponse.getResponse(key, object));
    }

    public static Response getResponse(String key, Object object, HttpStatus status) {
        return getResponse(JSONResponse.getResponse(key, object), status);
    }
}
