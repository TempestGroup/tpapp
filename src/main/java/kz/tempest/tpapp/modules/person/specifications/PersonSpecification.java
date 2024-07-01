package kz.tempest.tpapp.modules.person.specifications;

import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import kz.tempest.tpapp.commons.utils.StringUtil;
import kz.tempest.tpapp.modules.person.enums.Role;
import kz.tempest.tpapp.modules.person.models.Person;
import org.springframework.data.jpa.domain.Specification;
import java.util.List;
import java.util.Map;

public class PersonSpecification {
    public static Specification<Person> getSpecification(Person person, Map<String, Object> filter){
        return ((root, query, criteriaBuilder) -> {
            Predicate predication = criteriaBuilder.conjunction();
            for (String key: filter.keySet()) {
                Object value = filter.get(key);
                if (key.equals("name") && StringUtil.isNotEmpty((String) value)) {
                    predication = criteriaBuilder.or(
                            criteriaBuilder.like(root.get("email"), "%" + (String) value + "%")
                    );
                }
                if (key.equals("roles") && !((List<Role>) value).isEmpty() && !person.isUser()) {
                    List<Role> roles = ((List<Role>) value);
                    Path<List<String>> rolesPath = root.join("roles");
                    Predicate rolePredicate = rolesPath.in(roles);
                    predication = criteriaBuilder.and(predication, rolePredicate);
                }
            }
            return predication;
        });
    }
}
