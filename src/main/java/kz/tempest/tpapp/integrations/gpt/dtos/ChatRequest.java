package kz.tempest.tpapp.integrations.gpt.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRequest {
    @JsonProperty("model")
    @Value("${openai.model}")
    private String model;
    @JsonProperty("prompt")
    private String prompt;
    @JsonProperty("temperature")
    private int temperature = 1;
    @JsonProperty("max_tokens")
    private int maxTokens = 256;
    @JsonProperty("top_p")
    private int topP = 1;
    @JsonProperty("frequency_penalty")
    private int frequencyPenalty = 0;
    @JsonProperty("presence_penalty")
    private int presencePenalty = 0;

    public ChatRequest(String prompt) {
        this.prompt = prompt;
    }
}
