package kz.tempest.tpapp.integrations.gpt.services;

import kz.tempest.tpapp.commons.utils.ApiUtil;
import kz.tempest.tpapp.commons.utils.MapperUtil;
import kz.tempest.tpapp.integrations.gpt.configurations.GptConfig;
import kz.tempest.tpapp.integrations.gpt.dtos.ChatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import javax.json.Json;
import javax.json.JsonArray;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GptService {
    private final GptConfig gptConfig;

    public String getResponse(String prompt) {
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(gptConfig.getRequestBody(prompt), gptConfig.getHeaders());
        ResponseEntity<String> response = ApiUtil.exchange(gptConfig.getApiUrl(), HttpMethod.POST, entity, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return fromResponse(response.getBody()).getChoices().get(0).getText();
        }
        return "Error: " + response.getStatusCode() + " - " + response.getBody();
    }

    private ChatResponse fromResponse(String response) {
//        List<ChatResponse.Choice> choices = new ArrayList<>();
//        JsonArray choicesArray = Json.createReader(new StringReader(response)).readObject().getJsonArray("choices");
//        for (int i = 0; i < choicesArray.size(); i++) {
//            choices.add(new ChatResponse.Choice(choicesArray.getJsonObject(i).getInt("index"), choicesArray.getJsonObject(i).getString("text")));
//        }
//        return new ChatResponse(choices);
        return MapperUtil.mapToObject(response, ChatResponse.class);
    }
}
