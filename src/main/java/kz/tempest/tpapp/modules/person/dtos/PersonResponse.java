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

    public static PersonResponse from(Person person, Language language) {
        PersonResponse personResponse = new PersonResponse();
        personResponse.id = person.getId();
        personResponse.email = person.getEmail();
        personResponse.roles = person.getRoles().stream().map((role) -> role.getName(language)).toList();
        personResponse.image = "/api/v1/auth/images/" + person.getId();
        personResponse.active = person.isActive();
        return personResponse;
    }
}
