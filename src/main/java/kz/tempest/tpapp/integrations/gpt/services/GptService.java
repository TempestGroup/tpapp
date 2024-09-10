package kz.tempest.tpapp.integrations.gpt.services;

import kz.tempest.tpapp.commons.utils.ApiUtil;
import kz.tempest.tpapp.commons.utils.MapperUtil;
import kz.tempest.tpapp.integrations.gpt.configs.GptConfig;
import kz.tempest.tpapp.integrations.gpt.dtos.ChatRequest;
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
        HttpEntity<ChatRequest> entity = new HttpEntity<>(new ChatRequest(prompt), gptConfig.getHeaders());
        ResponseEntity<ChatResponse> response = ApiUtil.exchange(gptConfig.getApiUrl(), HttpMethod.POST, entity, ChatResponse.class);
        return response.getStatusCode() == HttpStatus.OK ? response.getBody() : null;
    }
}
