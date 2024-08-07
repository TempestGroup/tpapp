package kz.tempest.tpapp.jobScheduler.managers;

import kz.tempest.tpapp.commons.utils.StringUtil;
import kz.tempest.tpapp.jobScheduler.dto.JobDTO;
import kz.tempest.tpapp.jobScheduler.enums.JobType;
import kz.tempest.tpapp.jobScheduler.models.Job;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class JobManager {

    private static JdbcTemplate jdbcTemplate;

    public JobManager(JdbcTemplate jdbcTemplate) {
        JobManager.jdbcTemplate = jdbcTemplate;
    }

    public static Job getJob(JobType type) {
        return jdbcTemplate.query("SELECT * FROM jobs WHERE type = ?", JobManager::mapRowToItem, type.name());
    }

    public static int getJobCount() {
        return JobType.values().length;
    }

    public static List<Job> getJobs() {
        return jdbcTemplate.query("SELECT * FROM jobs WHERE type IN (" +
                StringUtil.join(Arrays.stream(JobType.values()).map(type -> "'" + type.name() + "'").toList(), ", ")
                + ")", JobManager::mapRowToList);
    }

    public static boolean setJob(JobType type) {
        return setJob(new Job(type, type.getNameKK(), type.getNameRU(), type.getNameEN(), type.getCron(), type.isActive()));
    }

    public static boolean setJob(Job job) {
        String sql = "INSERT INTO jobs (type, name_kk, name_ru, name_en, cron, active) VALUES ('" + job.getType().name() + "'," +
                " '" + job.getNameKK() + "', '" + job.getNameRU() + "', '" + job.getNameEN() + "', '" + job.getCron() + "', " + job.isActive() + ") ON CONFLICT (type) DO UPDATE " +
                " SET name = EXCLUDED.name, cron = EXCLUDED.cron, active = EXCLUDED.active";
        jdbcTemplate.execute(sql);
        return true;
    }

    public static boolean setJobs(List<JobDTO> jobs) {
        boolean res = false;
        for (JobDTO job : jobs) {
            res = setJob(new Job(
                JobType.valueOf(job.getType()),
                job.getNameKK(), job.getNameRU(),
                job.getNameEN(), job.getCron(),
                job.isActive()
            ));
        }
        return res;
    }

    private static Job mapRowToItem(ResultSet rs) throws SQLException {
        if (rs.next()) {
            return new Job(
                JobType.valueOf(rs.getString("type")),
                rs.getString("name_kk"),
                rs.getString("name_ru"),
                rs.getString("name_en"),
                rs.getString("cron"),
                rs.getBoolean("active")
            );
        }
        return null;
    }

    private static List<Job> mapRowToList(ResultSet rs) throws SQLException {
        List<Job> jobs = new ArrayList<>();
        while (rs.next()) {
            Job job = new Job(
                JobType.valueOf(rs.getString("type")),
                rs.getString("name_kk"),
                rs.getString("name_ru"),
                rs.getString("name_en"),
                rs.getString("cron"),
                rs.getBoolean("active")
            );
            jobs.add(job);
        }
        return jobs;
    }

    public static void initJobs() {
        for (JobType type : JobType.values()) {
            if (getJob(type) == null) {
                setJob(type);
            }
        }
    }
}

