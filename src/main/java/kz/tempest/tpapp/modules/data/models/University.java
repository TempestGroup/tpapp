package kz.tempest.tpapp.modules.data.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import kz.tempest.tpapp.commons.enums.Language;
import kz.tempest.tpapp.commons.utils.ClassUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "universities")
@Entity(name = "universities")
@NoArgsConstructor
@AllArgsConstructor
public class University {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    @Column(name = "name_kk", columnDefinition = "TEXT")
    private String nameKK;
    @Column(name = "name_ru", columnDefinition = "TEXT")
    private String nameRU;
    @Column(name = "name_en", columnDefinition = "TEXT")
    private String nameEN;
    @Column(name = "code")
    private String code;
    @Column(name = "exist")
    private boolean exist;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "city_id")
    private City city;

//    public University (Long id, String nameKK, String nameRU, String nameEN, String code, boolean exist, City city) {
//        this.id = id;
//        this.nameKK = nameKK;
//        this.nameRU = nameRU;
//        this.nameEN = nameEN;
//        this.code = code;
//        this.exist = exist;
//        this.city = city;
//    }

    public String getName(Language language) {
        return (String) ClassUtil.getLocalizedFieldValue(getClass(), this, "name", language);
    }
}
