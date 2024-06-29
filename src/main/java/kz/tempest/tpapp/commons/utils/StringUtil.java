package kz.tempest.tpapp.commons.utils;

import java.util.Collection;

public class StringUtil {
    public static boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }

    public static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }

    public static String join(Collection<String> collection, String delimiter) {
        return String.join(delimiter, collection);
    }
}
