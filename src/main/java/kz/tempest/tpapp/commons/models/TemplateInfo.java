package kz.tempest.tpapp.commons.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import kz.tempest.tpapp.commons.enums.Language;
import kz.tempest.tpapp.commons.utils.ClassUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashMap;
import java.util.Map;

@Data
@Table(name = "templates")
@Entity(name = "templates")
@NoArgsConstructor
@AllArgsConstructor
public class TemplateInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    @Column(name = "name_kk", columnDefinition = "TEXT")
    private String nameKK;
    @Column(name = "name_ru", columnDefinition = "TEXT")
    private String nameRU;
    @Column(name = "name_en", columnDefinition = "TEXT")
    private String nameEN;
    @JsonIgnore
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "template_files", joinColumns = @JoinColumn(name = "template_id"))
    @MapKeyColumn(name = "language")
    @MapKeyEnumerated(EnumType.STRING)
    private Map<Language, IFile> files = new HashMap<>();

    public TemplateInfo(String nameKK, String nameRU, String nameEN) {
        this.nameKK = nameKK;
        this.nameRU = nameRU;
        this.nameEN = nameEN;
    }

    public TemplateInfo(String nameKK, String nameRU, String nameEN, Map<Language, IFile> files) {
        this.nameKK = nameKK;
        this.nameRU = nameRU;
        this.nameEN = nameEN;
        this.files = files;
    }

    public TemplateInfo(String nameKK, String nameRU, String nameEN, IFile fileKK, IFile fileRU, IFile fileEN) {
        this.nameKK = nameKK;
        this.nameRU = nameRU;
        this.nameEN = nameEN;
        this.files = new HashMap<>() {{
            put(Language.kk, fileKK);
            put(Language.ru, fileRU);
            put(Language.en, fileEN);
        }};
    }

    public String getName(Language language) {
        return (String) ClassUtil.getLocalizedFieldValue(getClass(), this, "name", language);
    }
}
