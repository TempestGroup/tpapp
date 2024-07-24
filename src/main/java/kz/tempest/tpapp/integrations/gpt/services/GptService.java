package kz.tempest.tpapp.integrations.gpt.services;

import kz.tempest.tpapp.commons.utils.ApiUtil;
import kz.tempest.tpapp.commons.utils.MapperUtil;
import kz.tempest.tpapp.integrations.gpt.configs.GptConfig;
import kz.tempest.tpapp.integrations.gpt.dtos.ChatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class GptService {
    private final GptConfig gptConfig;

    public ChatResponse getResponse(String prompt) {
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(gptConfig.getRequestBody(prompt), gptConfig.getHeaders());
        ResponseEntity<String> response = ApiUtil.exchange(gptConfig.getApiUrl(), HttpMethod.POST, entity, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return MapperUtil.mapJsonToObject(response.getBody(), ChatResponse.class);
        }
        return null;
    }
}
