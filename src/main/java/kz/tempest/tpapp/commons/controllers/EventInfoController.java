package kz.tempest.tpapp.commons.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import kz.tempest.tpapp.commons.annotations.access.AccessChecker;
import kz.tempest.tpapp.commons.contexts.LanguageContext;
import kz.tempest.tpapp.commons.dtos.EventInfoResponse;
import kz.tempest.tpapp.commons.dtos.JSONResponse;
import kz.tempest.tpapp.commons.dtos.Response;
import kz.tempest.tpapp.commons.dtos.SearchFilter;
import kz.tempest.tpapp.commons.models.EventInfo;
import kz.tempest.tpapp.commons.services.EventInfoService;
import kz.tempest.tpapp.modules.person.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/events")
@Tag(name = "Event APIs", description = "API for person events")
public class EventInfoController {
    private final EventInfoService eventInfoService;

    @ResponseBody
    @PostMapping("/")
    @AccessChecker(roles = { Role.ADMIN })
    public Response getEvents(@RequestBody SearchFilter searchFilter) {
        return Response.getResponse(JSONResponse.getResponse("events", eventInfoService.getEvents(searchFilter, LanguageContext.getLanguage())));
    }

    @ResponseBody
    @GetMapping("/{eventID}")
    @AccessChecker(roles = { Role.ADMIN })
    public Response getEventByID(@PathVariable("eventID") EventInfo eventInfo) {
        return Response.getResponse(JSONResponse.getResponse("event", EventInfoResponse.from(eventInfo, LanguageContext.getLanguage())));
    }
}
