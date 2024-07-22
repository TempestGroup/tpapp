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
    @Column(name = "name_kk")
    private String nameKK;
    @Column(name = "name_ru")
    private String nameRU;
    @Column(name = "name_en")
    private String nameEN;
    // lastname
    @Column(name = "lastname_kk")
    private String lastnameKK;
    @Column(name = "lastname_ru")
    private String lastnameRU;
    @Column(name = "lastname_en")
    private String lastnameEN;
    // surname
    @Column(name = "surname_exist", columnDefinition = "TINYINT")
    private boolean isSurnameExist = true;
    @Column(name = "surname_kk")
    private String surnameKK;
    @Column(name = "surname_ru")
    private String surnameRU;
    @Column(name = "surname_en")
    private String surnameEN;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne
    @JoinColumn(name = "nationality_id")
    private Nationality nationality;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "iin_exist", columnDefinition = "TINYINT")
    private boolean isIINExist = true;

    @Column(name = "iin")
    private String IIN;

    public String getName(Language language) {
        return (String) ClassUtil.getLocalizedFieldValue(getClass(), this, "name", language);
    }

    public String getLastname(Language language) {
        return (String) ClassUtil.getLocalizedFieldValue(getClass(), this, "lastname", language);
    }

    public String getSurname(Language language) {
        return isSurnameExist ? (String) ClassUtil.getLocalizedFieldValue(getClass(), this, "surname", language) : "";
    }

    public void setSurnameKK(String surnameKK) {
        if (isSurnameExist) {
            this.surnameKK = surnameKK;
        }
    }

    public void setSurnameRU(String surnameRU) {
        if (isSurnameExist) {
            this.surnameRU = surnameRU;
        }
    }

    public void setSurnameEN(String surnameEN) {
        if (isSurnameExist) {
            this.surnameEN = surnameEN;
        }
    }

    public String getIIN() {
        return isIINExist ? IIN : "";
    }

    public void setIIN(String IIN) {
        if (isIINExist) {
            this.IIN = IIN;
        }
    }

}
