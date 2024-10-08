package kz.tempest.tpapp.jobScheduler.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import kz.tempest.tpapp.commons.annotations.access.AccessChecker;
import kz.tempest.tpapp.commons.dtos.Response;
import kz.tempest.tpapp.jobScheduler.ScheduleRunner;
import kz.tempest.tpapp.jobScheduler.dto.JobDTO;
import kz.tempest.tpapp.modules.person.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/jobs")
@RequiredArgsConstructor
@Tag(name = "Job APIs", description = "API for jobs data")
public class JobController {

    private final ScheduleRunner scheduleRunner;

    @ResponseBody
    @GetMapping("/get")
    @AccessChecker(roles = { Role.ADMIN, Role.ANALYST, Role.DEVELOPER })
    public Response getJobs() {
        return Response.getResponse("list", scheduleRunner.getJobs());
    }

    @ResponseBody
    @PostMapping("/save")
    @AccessChecker(roles = { Role.ADMIN, Role.ANALYST, Role.DEVELOPER })
    public Response saveJobs(@RequestBody List<JobDTO> jobDTOs) {
        return Response.getResponse("message", scheduleRunner.updateJobs(jobDTOs));
    }
}
