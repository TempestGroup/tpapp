package kz.tempest.tpapp.commons.utils;

import kz.tempest.tpapp.commons.enums.Language;
import org.springframework.data.domain.Sort;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SortUtil {

    public static List<Sort.Order> getSortOrders(Class iClass, Map<String, Sort.Direction> filterSorts, Language language) {
        String fieldName;
        List<Sort.Order> sorts = new ArrayList<>();
        for (Map.Entry<String, Sort.Direction> e: filterSorts.entrySet()) {
            if (ClassUtil.getField(iClass, e.getKey() + language.suffix()) != null) {
                fieldName = e.getKey() + language.suffix();
            } else {
                fieldName = e.getKey();
            }
            sorts.add(new Sort.Order(e.getValue(), fieldName));
        }
        return sorts;
    }
}
