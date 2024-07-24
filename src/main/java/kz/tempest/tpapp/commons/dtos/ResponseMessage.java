package kz.tempest.tpapp.commons.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import kz.tempest.tpapp.commons.enums.RMStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMessage {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String content;
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    private RMStatus status;

    public void set(String content, RMStatus status) {
        this.content = content;
        this.status = status;
    }
}
