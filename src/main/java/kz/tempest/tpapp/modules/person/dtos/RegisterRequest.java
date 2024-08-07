package kz.tempest.tpapp.modules.person.dtos;

import kz.tempest.tpapp.commons.annotations.validation.Validation;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class RegisterRequest {
    private Long id = 0L;
    @Validation(message = "username_is_incorrect_in_requirements", nullable = false, email = true)
    private String email;
    @Validation(message = "password_is_incorrect_in_requirements", nullable = false, min = 6)
    private String password;
    private MultipartFile image;
    private boolean active = false;
}
