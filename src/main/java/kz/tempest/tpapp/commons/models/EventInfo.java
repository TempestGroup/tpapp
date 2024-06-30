package kz.tempest.tpapp.commons.models;

import jakarta.persistence.*;
import kz.tempest.tpapp.commons.enums.EventType;
import kz.tempest.tpapp.commons.enums.Language;
import kz.tempest.tpapp.commons.enums.Module;
import kz.tempest.tpapp.commons.utils.ClassUtil;
import kz.tempest.tpapp.modules.person.models.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "event_infos")
@Entity(name = "event_infos")
@NoArgsConstructor
@AllArgsConstructor
public class EventInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "module")
    @Enumerated(EnumType.STRING)
    private Module module;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private EventType type;
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;
    @Column(name = "object_id")
    private Long objectID;
    @Column(name = "content_kk", columnDefinition = "TEXT")
    private String contentKK;
    @Column(name = "content_ru", columnDefinition = "TEXT")
    private String contentRU;
    @Column(name = "content_en", columnDefinition = "TEXT")
    private String contentEN;

    public String getContent(Language language) {
        return (String) ClassUtil.getLocalizedFieldValue(getClass(), this, "content", language);
    }
}
