package kz.tempest.tpapp.commons.models;

import jakarta.persistence.*;
import kz.tempest.tpapp.commons.enums.Language;
import kz.tempest.tpapp.commons.enums.SettingType;
import kz.tempest.tpapp.commons.services.SettingService;
import kz.tempest.tpapp.commons.utils.ClassUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Table(name = "settings")
@Entity(name = "settings")
@NoArgsConstructor
@AllArgsConstructor
public class Setting {
    @Id @Column(name = "key", columnDefinition = "TEXT")
    private String key;
    @Column(name = "name_kk")
    private String nameKK;
    @Column(name = "name_ru")
    private String nameRU;
    @Column(name = "name_en")
    private String nameEN;
    @Column(name = "value", columnDefinition = "TEXT")
    private String value;
    @Enumerated(EnumType.STRING)
    private SettingType type;

    public Setting(String key, String nameKK, String nameRU, String nameEN, Object value) {
        this.key = key;
        this.nameKK = nameKK;
        this.nameRU = nameRU;
        this.nameEN = nameEN;
        this.value = getValueString(value);
        this.type = getType(value);
    }

    public String getName(Language language) {
        return (String) ClassUtil.getLocalizedFieldValue(getClass(), this, "name", language);
    }

    private String getValueString(Object value) {
        if (value instanceof LocalDate) {
            return ((LocalDate) value).format(SettingService.formatter);
        }
        return String.valueOf(value);
    }

    private SettingType getType(Object value) {
        if (value instanceof Integer) {
            return SettingType.INTEGER;
        } else if (value instanceof Double) {
            return SettingType.DOUBLE;
        } else if (value instanceof LocalDate) {
            return SettingType.DATE;
        } else if (value instanceof List<?> list) {
            for (Object element : list) {
                if (element instanceof Integer) {
                    return SettingType.LIST_INTEGER;
                } else if (element instanceof Double) {
                    return SettingType.LIST_DOUBLE;
                } else if (element instanceof String) {
                    return SettingType.LIST_STRING;
                }
            }
        }
        return SettingType.STRING;
    }
}
