package kz.tempest.tpapp.commons.configs;

import io.jsonwebtoken.Header;
import kz.tempest.tpapp.commons.utils.MapperUtil;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Response extends ResponseEntity<Object> {

    public Response(Object body) {
        super(body, defaultHeaders(), HttpStatus.OK);
    }

    private static HttpHeaders defaultHeaders() {
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
}
