package kz.tempest.tpapp.integrations.gpt.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ChatResponse {
    @JsonProperty("choices")
    private List<Choice> choices;

    public static class Choice {

        @JsonProperty("index")
        private int index;
        @JsonProperty("text")
        private String text;

        public Choice(int index, String text) {
            this.index = index;
            this.text = text;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}

