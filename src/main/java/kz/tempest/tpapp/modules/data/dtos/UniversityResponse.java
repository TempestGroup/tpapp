package kz.tempest.tpapp.modules.data.dtos;

import kz.tempest.tpapp.commons.enums.Language;
import kz.tempest.tpapp.modules.data.models.University;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UniversityResponse {
    private Long ID;
    private String name;
    private String nameKK;
    private String nameRU;
    private String nameEN;
    private String code;
    private boolean exist;
    private CityResponse city;

    public static UniversityResponse from(University university, Language language) {
        return new UniversityResponse(university.getID(), university.getName(language), university.getNameKK(),
                university.getNameRU(), university.getNameEN(), university.getCode(), university.isExist(),
                CityResponse.from(university.getCity(), language));
    }
}
