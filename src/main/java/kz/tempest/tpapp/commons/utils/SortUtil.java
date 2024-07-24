package kz.tempest.tpapp.commons.utils;

import kz.tempest.tpapp.commons.enums.Language;
import org.springframework.data.domain.Sort;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SortUtil {

    public static List<Sort.Order> getSortOrders(Class iClass, Map<String, Sort.Direction> filterSorts, Language language) {
        if (filterSorts == null || filterSorts.isEmpty()) {
            return new ArrayList<>();
        }
        String fieldName = null;
        List<Sort.Order> sorts = new ArrayList<>();
        for (Map.Entry<String, Sort.Direction> e: filterSorts.entrySet()) {
            if (ClassUtil.hasLocalizedField(iClass, e.getKey(), language)) {
                fieldName = ClassUtil.getLocalizedFieldName(iClass, e.getKey(), language);
            } else if (ClassUtil.hasField(iClass, e.getKey())) {
                fieldName = ClassUtil.getFieldName(iClass, e.getKey());
            }

            if (fieldName != null) {
                sorts.add(new Sort.Order(e.getValue(), fieldName));
            }
        }
        return sorts;
    }
}
