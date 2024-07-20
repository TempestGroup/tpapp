package kz.tempest.tpapp.modules.data.dtos;

import kz.tempest.tpapp.commons.enums.Language;
import kz.tempest.tpapp.modules.data.models.City;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityResponse {
    private Long ID;
    private String name;
    private String nameKK;
    private String nameRU;
    private String nameEN;
    private CountryResponse country;

    public static CityResponse from(City city, Language language) {
        return new CityResponse(city.getID(), city.getName(language), city.getNameKK(), city.getNameRU(),
                city.getNameEN(), CountryResponse.from(city.getCountry(), language));
    }
}
