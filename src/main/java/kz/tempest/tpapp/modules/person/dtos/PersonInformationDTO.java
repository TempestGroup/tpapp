package kz.tempest.tpapp.modules.person.dtos;

import kz.tempest.tpapp.commons.utils.StringUtil;
import kz.tempest.tpapp.modules.person.models.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonInformationDTO {
    private Long personID;
    private String nameCyrillic;
    private String nameLatin;
    private String lastnameCyrillic;
    private String lastnameLatin;
    private String phoneNumber;

    public static PersonInformationDTO from(Person person) {
        return new PersonInformationDTO(
            person.getID() == null ? 0 : person.getID(),
            person.getInformation() == null || StringUtil.isEmpty(person.getInformation().getNameCyrillic()) ? "" : person.getInformation().getNameCyrillic(),
            person.getInformation() == null || StringUtil.isEmpty(person.getInformation().getNameLatin()) ? "" : person.getInformation().getNameLatin(),
            person.getInformation() == null || StringUtil.isEmpty(person.getInformation().getLastnameCyrillic()) ? "" : person.getInformation().getLastnameCyrillic(),
            person.getInformation() == null || StringUtil.isEmpty(person.getInformation().getLastnameLatin()) ? "" : person.getInformation().getLastnameLatin(),
            person.getInformation() == null || StringUtil.isEmpty(person.getInformation().getPhoneNumber()) ? "" : person.getInformation().getPhoneNumber()
        );
    }
}
