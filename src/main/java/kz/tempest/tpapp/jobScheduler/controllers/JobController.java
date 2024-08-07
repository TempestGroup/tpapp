package kz.tempest.tpapp.jobScheduler.controllers;

import kz.tempest.tpapp.commons.dtos.Response;
import kz.tempest.tpapp.jobScheduler.ScheduleRunner;
import kz.tempest.tpapp.jobScheduler.dto.JobDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/jobs")
@RequiredArgsConstructor
public class JobController {

    private final ScheduleRunner scheduleRunner;

    @ResponseBody
    @GetMapping("/get")
    public Response getJobs() {
        return Response.getResponse("list", scheduleRunner.getJobs());
    }

    @PostMapping("/save")
    public Response saveJobs(@RequestBody List<JobDTO> jobDTOs) {
        return Response.getResponse("message", scheduleRunner.updateJobs(jobDTOs));
    }
}
