package kz.tempest.tpapp.jobScheduler;

import jakarta.annotation.PostConstruct;
import kz.tempest.tpapp.commons.dtos.ResponseMessage;
import kz.tempest.tpapp.commons.enums.RMStatus;
import kz.tempest.tpapp.commons.utils.TranslateUtil;
import kz.tempest.tpapp.jobScheduler.dto.JobDTO;
import kz.tempest.tpapp.jobScheduler.enums.JobType;
import kz.tempest.tpapp.jobScheduler.managers.JobManager;
import kz.tempest.tpapp.jobScheduler.models.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

@Component
@EnableScheduling
public class ScheduleRunner {

    private final TaskScheduler taskScheduler;
    private final Map<String, ScheduledFuture<?>> jobs = new HashMap<>();
    private final Map<String, String> cronExpressions = new HashMap<>();
    private final ApplicationContext context;

    @Autowired
    public ScheduleRunner(ApplicationContext context) {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(JobType.values().length);
        scheduler.initialize();
        this.taskScheduler = scheduler;
        this.context = context;
    }

    public List<JobDTO> getJobs () {
        return JobManager.getJobs().stream().map(JobDTO::from).toList();
    }

    public boolean saveJobs (List<JobDTO> jobDTOs) {
        return JobManager.setJobs(jobDTOs);
    }

    public void scheduleJob(String jobId, String cronExpression) {
        JobType jobType = JobType.valueOf(jobId.toUpperCase());
        JobRunner jobInstance = context.getBean(jobType.getJobRunner());
        ScheduledFuture<?> future = taskScheduler.schedule(jobInstance::run, new CronTrigger(cronExpression));
        jobs.put(jobId, future);
        cronExpressions.put(jobId, cronExpression);
    }

    public ResponseMessage updateJobs(List<JobDTO> jobDTOs) {
        saveJobs(jobDTOs);
        for (JobType jobType : JobType.values()) {
            Job job = JobManager.getJob(jobType);
            ScheduledFuture<?> future = jobs.get(jobType.name());
            if (future != null) {
                future.cancel(true);
            }
            if (job.isActive()) {
                scheduleJob(jobType.name().toLowerCase(), job.getCron());
            }
        }
        return new ResponseMessage(TranslateUtil.getMessage("successfully_saved"), RMStatus.SUCCESS);
    }

    @PostConstruct
    public void initializeJobs() {
        JobManager.initJobs();
        for (JobType jobType : JobType.values()) {
            Job job = JobManager.getJob(jobType);
            if (job.isActive()) {
                scheduleJob(jobType.name().toLowerCase(), job.getCron());
            }
        }
    }

}
