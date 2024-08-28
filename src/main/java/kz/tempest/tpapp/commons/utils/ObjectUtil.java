package kz.tempest.tpapp.commons.utils;

import java.util.Objects;

public class ObjectUtil {

    public static boolean isEqual(Object obj1, Object obj2) {
        return Objects.equals(obj1, obj2);
    }

    public static boolean isNotNull(Object obj) {
        return !isNull(obj);
    }

    public static boolean isNull(Object obj) {
        return obj == null;
    }

}
