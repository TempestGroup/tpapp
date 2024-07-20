package kz.tempest.tpapp.commons.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import kz.tempest.tpapp.commons.enums.Language;
import kz.tempest.tpapp.commons.enums.Module;
import kz.tempest.tpapp.commons.utils.ClassUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "menu_items")
@Entity(name = "menu_items")
@NoArgsConstructor
@AllArgsConstructor
public class MenuItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    @Column(name = "name_kk", columnDefinition = "TEXT")
    private String nameKK;
    @Column(name = "name_ru", columnDefinition = "TEXT")
    private String nameRU;
    @Column(name = "name_en", columnDefinition = "TEXT")
    private String nameEN;
    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private Module module;

    public String getName(Language language) {
        return (String) ClassUtil.getLocalizedFieldValue(getClass(), this, "name", language);
    }
}
