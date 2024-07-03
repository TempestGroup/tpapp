package kz.tempest.tpapp.commons.controllers;

import kz.tempest.tpapp.commons.dtos.EventInfoResponse;
import kz.tempest.tpapp.commons.dtos.Response;
import kz.tempest.tpapp.commons.dtos.SearchFilter;
import kz.tempest.tpapp.commons.enums.Language;
import kz.tempest.tpapp.commons.models.EventInfo;
import kz.tempest.tpapp.commons.services.EventInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/events")
public class EventInfoController {
    private final EventInfoService eventInfoService;

    @ResponseBody
    @PostMapping("/")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Response getEvents(@RequestHeader(value = "Current-Language", defaultValue = "ru") Language language, @RequestBody SearchFilter searchFilter) {
        return Response.getResponse("events", eventInfoService.getEvents(searchFilter, language));
    }

    @ResponseBody
    @GetMapping("/{eventID}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Response getEventByID(@RequestHeader(value = "Current-Language", defaultValue = "ru") Language language, @PathVariable("eventID") EventInfo eventInfo) {
        return Response.getResponse("event", EventInfoResponse.from(eventInfo, language));
    }
}
