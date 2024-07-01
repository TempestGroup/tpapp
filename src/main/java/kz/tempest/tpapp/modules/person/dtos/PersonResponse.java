package kz.tempest.tpapp.modules.person.dtos;

import kz.tempest.tpapp.commons.enums.Language;
import kz.tempest.tpapp.commons.utils.HttpUtil;
import kz.tempest.tpapp.modules.person.models.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonResponse {
    private Long id;
    private String email;
    private List<String> roles;
    private String image;
    private boolean active;

    public static PersonResponse from(Person user, Language language) {
        PersonResponse userResponse = new PersonResponse();
        userResponse.id = user.getId();
        userResponse.email = user.getEmail();
        userResponse.roles = user.getRoles().stream().map((role) -> role.getName(language)).toList();
        userResponse.image = "/api/v1/auth/images/" + user.getId();
        userResponse.active = user.isActive();
        return userResponse;
    }
}
