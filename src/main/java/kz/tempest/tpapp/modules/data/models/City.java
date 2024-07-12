package kz.tempest.tpapp.modules.data.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import kz.tempest.tpapp.commons.enums.Language;
import kz.tempest.tpapp.commons.utils.ClassUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Table(name = "cities")
@Entity(name = "cities")
@NoArgsConstructor
@AllArgsConstructor
public class City {
    @Id
    private Long id;
    @Column(name = "name_kk", columnDefinition = "TEXT")
    private String nameKK;
    @Column(name = "name_ru", columnDefinition = "TEXT")
    private String nameRU;
    @Column(name = "name_en", columnDefinition = "TEXT")
    private String nameEN;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "country_id")
    private Country country;
    @OneToMany(mappedBy = "city")
    private List<University> universities;

    public City(Long id, String nameKK, String nameRU, String nameEN, Country country) {
        this.id = id;
        this.nameKK = nameKK;
        this.nameRU = nameRU;
        this.nameEN = nameEN;
        this.country = country;
    }

    public String getName(Language language) {
        return (String) ClassUtil.getLocalizedFieldValue(getClass(), this, "name", language);
    }
}
