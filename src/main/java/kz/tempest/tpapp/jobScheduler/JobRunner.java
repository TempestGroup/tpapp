package kz.tempest.tpapp.jobScheduler;

import kz.tempest.tpapp.commons.enums.Language;
import kz.tempest.tpapp.commons.enums.LogType;
import kz.tempest.tpapp.commons.utils.LogUtil;
import kz.tempest.tpapp.jobScheduler.enums.JobType;

public abstract class JobRunner {

    public JobType getType() {
        return JobType.getByRunner(getClass());
    }

    public abstract void execute();

    public void run() {
        log();
        execute();
    }

    public void log() {
        LogUtil.write("Starting '" + getType().getName(Language.en) + " (" + getType().getJobRunner().getSimpleName() + ")'!", LogType.INFO);
    }
}
