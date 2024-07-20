package kz.tempest.tpapp.commons.dtos;

import kz.tempest.tpapp.commons.enums.Language;
import kz.tempest.tpapp.commons.models.EventInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventInfoResponse {
    private Long id;
    private SimpleObject module;
    private SimpleObject type;
    private SimpleObject person;
    private Long objectID;
    private String content;
    private String contentKK;
    private String contentRU;
    private String contentEN;
    private String host;
    private LocalDateTime time = LocalDateTime.now(ZoneId.of("Asia/Almaty"));

    public static EventInfoResponse from(EventInfo eventInfo, Language language) {
        EventInfoResponse eventInfoResponse = new EventInfoResponse();
        eventInfoResponse.setId(eventInfo.getID());
        eventInfoResponse.setModule(new SimpleObject(eventInfo.getModule().getCode(), eventInfo.getModule().getName(language)));
        eventInfoResponse.setModule(new SimpleObject(eventInfo.getType().name(), eventInfo.getType().getName(language)));
        eventInfoResponse.setPerson(new SimpleObject(eventInfo.getPerson().getID(), eventInfo.getPerson().getUsername()));
        eventInfoResponse.setObjectID(eventInfo.getObjectID());
        eventInfoResponse.setContent(eventInfo.getContent(language));
        eventInfoResponse.setContentKK(eventInfo.getContentKK());
        eventInfoResponse.setContentRU(eventInfo.getContentRU());
        eventInfoResponse.setContentEN(eventInfo.getContentEN());
        eventInfoResponse.setHost(eventInfo.getHost());
        eventInfoResponse.setTime(eventInfo.getTime());
        return eventInfoResponse;
    }
}
