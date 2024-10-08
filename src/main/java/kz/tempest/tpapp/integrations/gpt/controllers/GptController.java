package kz.tempest.tpapp.integrations.gpt.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import kz.tempest.tpapp.commons.dtos.Response;
import kz.tempest.tpapp.integrations.gpt.services.GptService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/gpt")
@Tag(name = "ChatGPT APIs", description = "API of Chat gpt")
public class GptController {

    private final GptService gptService;

    @GetMapping("/response")
    public Response getChatResponse(@RequestParam("prompt") String prompt) {
        return Response.getResponse(gptService.getResponse(prompt));
    }
}
