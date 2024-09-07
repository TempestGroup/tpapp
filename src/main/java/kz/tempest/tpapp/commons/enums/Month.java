package kz.tempest.tpapp.commons.enums;

import kz.tempest.tpapp.commons.utils.ClassUtil;
import kz.tempest.tpapp.commons.utils.LogUtil;
import java.util.HashMap;
import java.util.Map;

public enum Month {
    JANUARY(1, "Қаңтар", "Январь", "January"),
    FEBRUARY(2, "Ақпан", "Февраль", "February"),
    MARCH(3, "Наурыз", "Март", "March"),
    APRIL(4, "Сәуір", "Апрель", "April"),
    MAY(5, "Мамыр", "Май", "May"),
    JUNE(6, "Маусым", "Июнь", "June"),
    JULY(7, "Шілде", "Июль", "July"),
    AUGUST(8, "Тамыз", "Август", "August"),
    SEPTEMBER(9, "Қыркүйек", "Сентябрь", "September"),
    OCTOBER(10, "Қазан", "Октябрь", "October"),
    NOVEMBER(11, "Қараша", "Ноябрь", "November"),
    DECEMBER(12, "Желтоқсан", "Декабрь", "December");


    private final int id;
    private final String nameKK;
    private final String nameRU;
    private final String nameEN;
    private final static Map<Integer, Month> months = new HashMap<>();

    static {
        for (Month month : values()) {
            months.put(month.id, month);
        }
    }

    Month(int id, String nameKK, String nameRU, String nameEN) {
        this.id = id;
        this.nameKK = nameKK;
        this.nameRU = nameRU;
        this.nameEN = nameEN;
    }

    public static Month fromId (int id) {
        return months.get(id);
    }

    public int getId() {
        return id;
    }


    public String getName(Language language) {
        return (String) ClassUtil.getLocalizedFieldValue(getClass(), this, "name", language);
    }
}
