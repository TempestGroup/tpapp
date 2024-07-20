package kz.tempest.tpapp.commons.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import kz.tempest.tpapp.commons.enums.EventType;
import kz.tempest.tpapp.commons.enums.Language;
import kz.tempest.tpapp.commons.utils.ClassUtil;
import kz.tempest.tpapp.modules.person.models.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
@Table(name = "event_infos")
@Entity(name = "event_infos")
@NoArgsConstructor
@AllArgsConstructor
public class EventInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    @Enumerated(EnumType.STRING)
    private kz.tempest.tpapp.commons.enums.Module module;
    @JsonIgnore
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private EventType type;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id", nullable = true)
    private Person person;
    @Column(name = "object_id")
    private Long objectID;
    @Column(name = "content_kk", columnDefinition = "TEXT")
    private String contentKK;
    @Column(name = "content_ru", columnDefinition = "TEXT")
    private String contentRU;
    @Column(name = "content_en", columnDefinition = "TEXT")
    private String contentEN;
    @Column(name = "host")
    private String host;
    @Column(name = "time")
    private LocalDateTime time = LocalDateTime.now(ZoneId.of("Asia/Almaty"));

    public EventInfo (kz.tempest.tpapp.commons.enums.Module module, EventType type, Person person, Long objectID, String contentKK, String contentRU, String contentEN, String host) {
        this.module = module;
        this.type = type;
        this.person = person;
        this.objectID = objectID;
        this.contentKK = contentKK;
        this.contentRU = contentRU;
        this.contentEN = contentEN;
        this.host = host;
    }

    public String getContent(Language language) {
        return (String) ClassUtil.getLocalizedFieldValue(getClass(), this, "content", language);
    }
}
