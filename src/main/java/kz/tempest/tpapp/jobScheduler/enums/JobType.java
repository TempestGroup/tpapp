package kz.tempest.tpapp.jobScheduler.enums;

import kz.tempest.tpapp.commons.enums.Language;
import kz.tempest.tpapp.commons.utils.ClassUtil;
import kz.tempest.tpapp.jobScheduler.JobRunner;
import kz.tempest.tpapp.jobScheduler.jobs.UpdateUserJobRunner;

import java.util.HashMap;
import java.util.Map;

public enum JobType {

    UPDATE_USER_INFO(
        "Пайдаланушы ақпаратын жаңарту",
        "Обновление информации о пользователе",
        "Refreshing user info",
        UpdateUserJobRunner.class,
        "0 0 12 * * *",
        false
    );

    private final String nameKK;
    private final String nameRU;
    private final String nameEN;
    private final Class<? extends JobRunner> jobRunner;
    private final String cron;
    private final boolean active;

    private static final Map<Class<? extends JobRunner>, JobType> byRunner = new HashMap<>();

    static {
        for(JobType type : values()) {
            byRunner.put(type.jobRunner, type);
        }
    }

    JobType(String nameKK, String nameRU, String nameEN, Class<? extends JobRunner> jobRunner, String cron, boolean active) {
        this.nameKK = nameKK;
        this.nameRU = nameRU;
        this.nameEN = nameEN;
        this.jobRunner = jobRunner;
        this.cron = cron;
        this.active = active;
    }

    public static JobType getByRunner(Class<? extends JobRunner> jobRunner) {
        return byRunner.get(jobRunner);
    }

    public String getName(Language language) {
        return (String) ClassUtil.getLocalizedFieldValue(getClass(), this, "name", language);
    }

    public String getNameKK() {
        return nameKK;
    }

    public String getNameRU() {
        return nameRU;
    }

    public String getNameEN() {
        return nameEN;
    }

    public Class<? extends JobRunner> getJobRunner() {
        return jobRunner;
    }

    public String getCron() {
        return cron;
    }

    public boolean isActive() {
        return active;
    }
}
