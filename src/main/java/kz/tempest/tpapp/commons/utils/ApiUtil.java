package kz.tempest.tpapp.commons.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class ApiUtil {

    private final RestTemplate restTemplate;

    public <T> T getForObject(String url, Class<T> responseType) {
        return restTemplate.getForObject(url, responseType);
    }

    public <T> T postForObject(String url, Object request, Class<T> responseType) {
        return restTemplate.postForObject(url, request, responseType);
    }

    public void put(String url, Object request) {
        restTemplate.put(url, request);
    }

    public void delete(String url) {
        restTemplate.delete(url);
    }
}

