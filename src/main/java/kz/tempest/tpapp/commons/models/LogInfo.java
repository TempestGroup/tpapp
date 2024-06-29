package kz.tempest.tpapp.commons.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import kz.tempest.tpapp.commons.enums.LogType;
import kz.tempest.tpapp.modules.person.models.Person;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
@Table
@Entity(name = "log_infos")
@NoArgsConstructor
public class LogInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "date_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime dateTime = LocalDateTime.now(ZoneId.of("Asia/Almaty"));
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private LogType type;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id", nullable = true)
    private Person person;

    public LogInfo (String content, LogType type, Person person) {
        this.content = content;
        this.type = type;
        this.person = person;
    }
}
