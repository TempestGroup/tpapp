package kz.tempest.tpapp.jobScheduler.models;

import jakarta.persistence.*;
import kz.tempest.tpapp.jobScheduler.enums.JobType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name="jobs")
@NoArgsConstructor
public class Job {
    @Id
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private JobType type;
    @Column(name = "name_kk", columnDefinition = "TEXT")
    private String nameKK;
    @Column(name = "name_ru", columnDefinition = "TEXT")
    private String nameRU;
    @Column(name = "name_en", columnDefinition = "TEXT")
    private String nameEN;
    @Column(name = "cron")
    private String cron;
    @Column(name = "active")
    private boolean active;

    public Job(JobType type, String nameKK, String nameRU, String nameEN, String cron, boolean active) {
        this.type = type;
        this.nameKK = nameKK;
        this.nameRU = nameRU;
        this.nameEN = nameEN;
        this.cron = cron;
        this.active = active;
    }
}
