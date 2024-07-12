package kz.tempest.tpapp.modules.data.specifications;

import jakarta.persistence.criteria.Predicate;
import kz.tempest.tpapp.commons.utils.StringUtil;
import kz.tempest.tpapp.modules.data.models.Country;
import org.springframework.data.jpa.domain.Specification;
import java.util.Map;

public class CountrySpecification {
    public static Specification<Country> getSpecification(Map<String, Object> filter){
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
            }
            return predication;
        });
    }
}
