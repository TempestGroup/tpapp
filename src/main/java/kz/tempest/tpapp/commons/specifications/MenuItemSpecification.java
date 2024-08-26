package kz.tempest.tpapp.commons.specifications;

import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import kz.tempest.tpapp.commons.enums.Module;
import kz.tempest.tpapp.commons.models.MenuItem;
import kz.tempest.tpapp.commons.utils.StringUtil;
import org.springframework.data.jpa.domain.Specification;
import java.util.Map;

public class MenuItemSpecification {

    public static Specification<MenuItem> getSpecification(Map<String, Object> filter){
        return ((root, query, criteriaBuilder) -> {
            Predicate predication = criteriaBuilder.conjunction();
            for (String key: filter.keySet()) {
                Object value = filter.get(key);
                if (key.equals("name") && StringUtil.isNotEmpty((String) value)) {
                    predication = criteriaBuilder.or(
                            criteriaBuilder.like(root.get("nameKK"), "%" + (String) value + "%"),
                            criteriaBuilder.like(root.get("nameRU"), "%" + (String) value + "%"),
                            criteriaBuilder.like(root.get("nameEN"), "%" + (String) value + "%")
                    );
                }

                if (key.equals("module") && value instanceof String && !((String) value).isEmpty() && Module.contains((String) value)) {
                    Module module = Module.getByCode((String) value);
                    Path<String> modulePath = root.join("module");
                    Predicate modulePredicate = modulePath.in(module);
                    predication = criteriaBuilder.and(predication, modulePredicate);
                }
            }
            return predication;
        });
    }

}
