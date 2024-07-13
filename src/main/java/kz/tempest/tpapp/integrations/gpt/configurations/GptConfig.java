package kz.tempest.tpapp.integrations.gpt.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class GptConfig {
    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiUrl;

    public String getApiKey() {
        return apiKey;
    }

    public String getModel() {
        return model;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public HttpHeaders getHeaders() {
        return new HttpHeaders() {{
            set("Content-Type", "application/json");
            set("Authorization", "Bearer " + apiKey);
        }};
    }

    public Map<String, Object> getRequestBody(String prompt) {
        return new HashMap<>() {{
            put("model", "gpt-3.5-turbo-instruct");
            put("prompt", prompt);
            put("temperature", 1);
            put("max_tokens", 256);
            put("top_p", 1);
            put("frequency_penalty", 0);
            put("presence_penalty", 0);
        }};
    }
}
