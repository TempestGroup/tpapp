package kz.tempest.tpapp.modules.data.models;

import jakarta.persistence.*;
import kz.tempest.tpapp.commons.enums.Language;
import kz.tempest.tpapp.commons.utils.ClassUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Table(name = "countries")
@Entity(name = "countries")
@NoArgsConstructor
@AllArgsConstructor
public class Country {
    @Id
    private Long id;
    @Column(name = "name_kk", columnDefinition = "TEXT")
    private String nameKK;
    @Column(name = "name_ru", columnDefinition = "TEXT")
    private String nameRU;
    @Column(name = "name_en", columnDefinition = "TEXT")
    private String nameEN;
    @OneToMany(mappedBy = "country")
    private List<City> cities;

    public Country(Long id, String nameKK, String nameRU, String nameEN) {
        this.id = id;
        this.nameKK = nameKK;
        this.nameRU = nameRU;
        this.nameEN = nameEN;
    }

    public String getName(Language language) {
        return (String) ClassUtil.getLocalizedFieldValue(getClass(), this, "name", language);
    }
}
