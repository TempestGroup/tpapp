package kz.tempest.tpapp.commons.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import kz.tempest.tpapp.commons.annotations.access.AccessChecker;
import kz.tempest.tpapp.commons.configs.Response;
import kz.tempest.tpapp.commons.dtos.ResponseMessage;
import kz.tempest.tpapp.commons.enums.Language;
import kz.tempest.tpapp.commons.models.IFile;
import kz.tempest.tpapp.commons.services.IFileService;
import kz.tempest.tpapp.commons.utils.ResponseUtil;
import kz.tempest.tpapp.modules.person.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/files")
@Tag(name = "File APIs", description = "API for files")
public class IFileController {

    private final IFileService fileService;

    @ResponseBody
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @AccessChecker(anonymous = false)
    @Operation(summary = "Upload file into database")
    public Response upload(@Parameter(name = "file", description = "Uploading file with form/date") @ModelAttribute MultipartFile file) {
        ResponseMessage message = new ResponseMessage();
        fileService.save(file, message);
        return ResponseUtil.getResponse("message", message);
    }

    @ResponseBody
    @GetMapping(value = "/{fileID}")
    @AccessChecker(anonymous = false)
    public void get(@PathVariable("fileID") IFile file, HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.getOutputStream().write(file.getFile());
    }

    @ResponseBody
    @DeleteMapping(value = "/{fileID}")
    @AccessChecker(anonymous = false)
    public Response delete(@PathVariable("fileID") IFile file, @RequestHeader(name = "Language", defaultValue = "ru") Language language) {
        ResponseMessage message = new ResponseMessage();
        fileService.delete(file, message);
        return ResponseUtil.getResponse("message", message);
    }

}
