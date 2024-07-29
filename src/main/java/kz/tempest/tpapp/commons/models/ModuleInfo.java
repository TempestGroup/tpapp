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
@Table(name = "modules")
@Entity(name = "modules")
@NoArgsConstructor
@AllArgsConstructor
public class ModuleInfo {
    @Id @Column(name = "module")
    @Enumerated(EnumType.STRING)
    private Module module;
    @Lob
    @JsonIgnore
    @Column(name = "icon", columnDefinition = "LONGBLOB", nullable = true)
    private byte[] icon;

    public String getName(Language language) {
        return (String) ClassUtil.getLocalizedFieldValue(getClass(), this, "name", language);
    }
}
