package kz.tempest.tpapp.modules.person.dtos;

import kz.tempest.tpapp.commons.enums.Language;
import kz.tempest.tpapp.commons.utils.HttpUtil;
import kz.tempest.tpapp.commons.utils.StringUtil;
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
    private String name;

    public static PersonResponse from(Person person, Language language) {
        PersonResponse personResponse = new PersonResponse();
        personResponse.id = person.getID();
        personResponse.email = person.getEmail();
        personResponse.roles = person.getRoles().stream().map((role) -> role.getName(language)).toList();
        personResponse.image = "/api/v1/auth/images/" + person.getID();
        personResponse.active = person.isActive();
        personResponse.name = person.getInformation() != null && StringUtil.isNotEmpty(person.getInformation().getFullname(language)) ? person.getInformation().getFullname(language) : "";
        return personResponse;
    }
}
