package kz.tempest.tpapp.modules.person.dtos;

import jakarta.annotation.Nullable;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class RegisterRequest {
    private Long id = 0L;
    private String email;
    private String password;
    private MultipartFile image;
    private boolean active = false;
}
