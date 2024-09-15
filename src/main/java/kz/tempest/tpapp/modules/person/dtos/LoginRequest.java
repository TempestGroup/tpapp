package kz.tempest.tpapp.modules.person.dtos;

import kz.tempest.tpapp.commons.annotations.validation.Validation;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequest {
    @Validation(message = "username_is_incorrect_in_requirements", nullable = false, email = true)
    private String username;
    @Validation(message = "password_is_incorrect_in_requirements", nullable = false, min = 6)
    private String password;
    private boolean withMobileToken = false;
}
