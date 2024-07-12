package kz.tempest.tpapp.modules.data.dtos;

import kz.tempest.tpapp.commons.enums.Language;
import kz.tempest.tpapp.modules.data.models.Country;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryResponse {
    private Long id;
    private String name;
    private String nameKK;
    private String nameRU;
    private String nameEN;

    public static CountryResponse from(Country country, Language language) {
        return new CountryResponse(country.getId(), country.getName(language), country.getNameKK(), country.getNameRU(), country.getNameEN());
    }
}
