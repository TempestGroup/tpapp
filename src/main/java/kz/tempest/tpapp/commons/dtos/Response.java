package kz.tempest.tpapp.commons.dtos;

import org.springframework.http.*;
import org.springframework.util.MultiValueMap;

public class Response extends ResponseEntity<Object> {

    public Response(Object body) {
        super(body, defaultHeaders(), HttpStatus.OK);
    }

    private static MultiValueMap<String, String> defaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private static MultiValueMap<String, String> defaultHeaders(MultiValueMap<String, String> headers) {
        if (!headers.containsKey(HttpHeaders.CONTENT_TYPE)) {
            headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        }
        return headers;
    }

    public Response(HttpStatusCode status) {
        super(defaultHeaders(), status);
    }

    public Response(Object body, HttpStatusCode status) {
        super(body, defaultHeaders(), status);
    }

    public Response(MultiValueMap<String, String> headers, HttpStatusCode status) {
        super(headers, status);
    }

    public Response(Object body, MultiValueMap<String, String> headers, int rawStatus) {
        super(body, defaultHeaders(headers), rawStatus);
    }

    public Response(Object body, MultiValueMap<String, String> headers, HttpStatusCode statusCode) {
        super(body, defaultHeaders(headers), statusCode);
    }

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
