package kz.tempest.tpapp.jobScheduler.dto;

import kz.tempest.tpapp.jobScheduler.models.Job;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobDTO {
    private String type;
    private String nameKK;
    private String nameRU;
    private String nameEN;
    private String className;
    private String cron;
    private boolean active;

    public static JobDTO from(Job job) {
        return new JobDTO(job.getType().name(),
                job.getNameKK(), job.getNameRU(), job.getNameEN(),
                job.getType().getJobRunner().getSimpleName(), job.getCron(),
                job.isActive());
    }
}
