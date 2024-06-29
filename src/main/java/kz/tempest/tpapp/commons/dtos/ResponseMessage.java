package kz.tempest.tpapp.commons.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import kz.tempest.tpapp.commons.enums.ResponseMessageStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseMessage {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String content;
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    private ResponseMessageStatus status;
}
