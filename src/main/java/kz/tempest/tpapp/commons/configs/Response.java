package kz.tempest.tpapp.commons.configs;

import kz.tempest.tpapp.commons.utils.MapperUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Response extends ResponseEntity<Object> {

    public Response(Object object) {
        super(object, HttpStatus.OK);
    }

    public Response(HttpStatusCode status) {
        super(status);
    }

    public Response(Object body, HttpStatusCode status) {
        super(body, status);
    }

    public Response(MultiValueMap<String, String> headers, HttpStatusCode status) {
        super(headers, status);
    }

    public Response(Object body, MultiValueMap<String, String> headers, int rawStatus) {
        super(body, headers, rawStatus);
    }

    public Response(Object body, MultiValueMap<String, String> headers, HttpStatusCode statusCode) {
        super(body, headers, statusCode);
    }
}
