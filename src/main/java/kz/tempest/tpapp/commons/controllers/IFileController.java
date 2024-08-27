package kz.tempest.tpapp.commons.controllers;

import jakarta.servlet.http.HttpServletResponse;
import kz.tempest.tpapp.commons.configs.Response;
import kz.tempest.tpapp.commons.dtos.ResponseMessage;
import kz.tempest.tpapp.commons.enums.Language;
import kz.tempest.tpapp.commons.models.IFile;
import kz.tempest.tpapp.commons.services.IFileService;
import kz.tempest.tpapp.commons.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/files")
public class IFileController {

    private final IFileService fileService;

    @ResponseBody
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Response upload(@ModelAttribute MultipartFile file, @RequestHeader(name = "Language", defaultValue = "ru") Language language) {
        ResponseMessage message = new ResponseMessage();
        fileService.save(file, message);
        return ResponseUtil.getResponse("message", message);
    }

    @ResponseBody
    @GetMapping(value = "/{fileID}")
    public void get(@PathVariable("fileID") IFile file, @RequestHeader(name = "Language", defaultValue = "ru") Language language, HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.getOutputStream().write(file.getFile());
    }

    @ResponseBody
    @PostMapping(value = "/{fileID}")
    public Response delete(@PathVariable("fileID") IFile file, @RequestHeader(name = "Language", defaultValue = "ru") Language language) {
        ResponseMessage message = new ResponseMessage();
        fileService.delete(file, message);
        return ResponseUtil.getResponse("message", message);
    }

}
