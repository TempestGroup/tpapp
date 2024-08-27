package kz.tempest.tpapp.integrations.gpt.controllers;

import kz.tempest.tpapp.commons.configs.Response;
import kz.tempest.tpapp.integrations.gpt.services.GptService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/gpt")
public class GptController {
    private final GptService gptService;

    @GetMapping("/response")
    public Response getChatResponse(@RequestParam("prompt") String prompt) {
        return Response.getResponse(gptService.getResponse(prompt));
    }
}
