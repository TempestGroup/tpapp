package kz.tempest.tpapp.commons.models;

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
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name_kk")
    private String nameKK;
    @Column(name = "name_ru")
    private String nameRU;
    @Column(name = "name_en")
    private String nameEN;
    @Column(name = "link")
    private String link;
    @Column(name = "module")
    @Enumerated(EnumType.STRING)
    private Module module;
    @Column(name = "mobile_icon")
    private String mobileIcon;
    @Column(name = "web_icon")
    private String webIcon;

    public String getName(Language language) {
        return (String) ClassUtil.getLocalizedFieldValue(getClass(), this, "name", language);
    }

    public String getIcon() {
        return getIcon(false);
    }

    public String getIcon(boolean isMobile) {
        return isMobile ? mobileIcon : webIcon;
    }
}
