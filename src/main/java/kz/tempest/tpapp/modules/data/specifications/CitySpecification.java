package kz.tempest.tpapp.modules.data.specifications;

import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import kz.tempest.tpapp.commons.utils.StringUtil;
import kz.tempest.tpapp.modules.data.models.City;
import org.springframework.data.jpa.domain.Specification;
import java.util.List;
import java.util.Map;

public class CitySpecification {
    public static Specification<City> getSpecification(Map<String, Object> filter){
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
                if (key.equals("countryID") && ((Long) value) > 0) {
                    Path<List<String>> countryJoin = root.join("country");
                    Predicate countryPredicate = countryJoin.in(value);
                    predication = criteriaBuilder.and(predication, countryPredicate);
                }
            }
            return predication;
        });
    }
}
