package kz.tempest.tpapp.modules.person.models;

import jakarta.persistence.*;
import kz.tempest.tpapp.commons.enums.Language;
import kz.tempest.tpapp.commons.utils.ClassUtil;
import kz.tempest.tpapp.modules.data.models.City;
import kz.tempest.tpapp.modules.data.models.Nationality;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class PersonInformation {
    // name
    @Column(name = "name_cyrillic")
    private String nameCyrillic;
    @Column(name = "name_latin")
    private String nameLatin;
    // lastname
    @Column(name = "lastname_cyrillic")
    private String lastnameCyrillic;
    @Column(name = "lastname_latin")
    private String lastnameLatin;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne
    @JoinColumn(name = "nationality_id")
    private Nationality nationality;

    @Column(name = "phone_number")
    private String phoneNumber;

    public String getName(Language language) {
        return language == Language.en ? nameLatin : nameCyrillic;
    }

    public String getLastname(Language language) {
        return language == Language.en ? lastnameLatin : lastnameCyrillic;
    }

    public String getFullname(Language language) {
        return getName(language) + " " + getLastname(language);
    }

}
