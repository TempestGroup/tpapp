package kz.tempest.tpapp.modules.person.enums;

import kz.tempest.tpapp.commons.enums.Language;
import kz.tempest.tpapp.commons.utils.ClassUtil;
import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN("Әкімші", "Администратор", "Administrator"),
    DEVELOPER("Әзірлеуші", "Разработчик", "Developer"),
    ANALYST("Талдаушы", "Аналитик", "Analyst"),
    EMPLOYEE("Қызметкер", "Сотрудник", "Employee"),
    USER("Пайдаланушы", "Пользователь", "User");

    private final String nameKK;
    private final String nameRU;
    private final String nameEN;

    Role(
            String nameKK,
            String nameRU,
            String nameEN
    ){
        this.nameKK = nameKK;
        this.nameRU = nameRU;
        this.nameEN = nameEN;
    }

    public String getNameKK() {
        return nameKK;
    }

    public String getNameRU() {
        return nameRU;
    }

    public String getNameEN() {
        return nameEN;
    }

    public String getName(Language language) {
        return (String) ClassUtil.getLocalizedFieldValue(getClass(), this, "name", language);
    }

    @Override
    public String getAuthority() {
        return name();
    }
}
