package kz.tempest.tpapp.modules.data.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/universities")
@Tag(name = "University APIs", description = "API for universities data")
public class UniversityController {
}
