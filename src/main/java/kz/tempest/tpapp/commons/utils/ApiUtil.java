package kz.tempest.tpapp.commons.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

public class ApiUtil {

    private static final RestTemplate restTemplate = new RestTemplate();

    public static <T> ResponseEntity<T> exchange(String url, HttpMethod method, @Nullable HttpEntity<?> requestEntity, Class<T> responseType, Object... uriVariables) {
        return restTemplate.exchange(url, method, requestEntity, responseType, uriVariables);
    }

    public static <T> ResponseEntity<T> exchange(String url, HttpMethod method, @Nullable HttpEntity<?> requestEntity, Class<T> responseType, Map<String, ?> uriVariables) {
        return restTemplate.exchange(url, method, requestEntity, responseType, uriVariables);
    }

    public static <T> T get(String url, Class<T> responseType) {
        return restTemplate.getForObject(url, responseType);
    }

    public static String get(String url) {
        return get(url, String.class);
    }

    public static <T> T post(String url, Object request, Class<T> responseType) {
        return restTemplate.postForObject(url, request, responseType);
    }

    public static <T> T post(String url, Class<T> responseType) {
        return restTemplate.postForObject(url, null, responseType);
    }

    public static String post(String url, Object request) {
        return post(url, request, String.class);
    }

    public static String post(String url) {
        return post(url, null, String.class);
    }

    public static void put(String url, Object request) {
        restTemplate.put(url, request);
    }

    public static void delete(String url) {
        restTemplate.delete(url);
    }
}

