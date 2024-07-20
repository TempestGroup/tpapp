package kz.tempest.tpapp.commons.enums;

import kz.tempest.tpapp.commons.utils.ClassUtil;

public enum Right {
    WRITE("Өңдеу рұқсаты", "Доступ для редактирования", "Write access"),
    READ("Оқу рұқсаты", "Доступ для чтения", "Read access");

    private final String nameKK;
    private final String nameRU;
    private final String nameEN;

    Right(String nameKK, String nameRU, String nameEN) {
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
}
