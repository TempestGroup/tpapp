package kz.tempest.tpapp.modules.person.specifications;

import jakarta.persistence.criteria.*;
import kz.tempest.tpapp.commons.utils.StringUtil;
import kz.tempest.tpapp.modules.data.models.City;
import kz.tempest.tpapp.modules.data.models.Nationality;
import kz.tempest.tpapp.modules.person.enums.Role;
import kz.tempest.tpapp.modules.person.models.Person;
import kz.tempest.tpapp.modules.person.models.PersonInformation;
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
                    if (person.isStaff()) {
                        predication = criteriaBuilder.or(
                                criteriaBuilder.like(root.get("email"), "%" + value + "%"),
                                criteriaBuilder.like(root.get("information").get("nameKK"), "%" + value + "%"),
                                criteriaBuilder.like(root.get("information").get("nameRU"), "%" + value + "%"),
                                criteriaBuilder.like(root.get("information").get("nameEN"), "%" + value + "%"),
                                criteriaBuilder.like(root.get("information").get("lastnameKK"), "%" + value + "%"),
                                criteriaBuilder.like(root.get("information").get("lastnameRU"), "%" + value + "%"),
                                criteriaBuilder.like(root.get("information").get("lastnameEN"), "%" + value + "%"),
                                criteriaBuilder.like(root.get("information").get("surnameKK"), "%" + value + "%"),
                                criteriaBuilder.like(root.get("information").get("surnameRU"), "%" + value + "%"),
                                criteriaBuilder.like(root.get("information").get("surnameEN"), "%" + value + "%")
                        );
                    } else {
                        predication = criteriaBuilder.or(
                                criteriaBuilder.like(root.get("email"), "%" + value + "%")
                        );
                    }
                }

                if (key.equals("roles") && !((List<String>) value).isEmpty() && person.isStaff()) {
                    List<String> roles = ((List<String>) value);
                    Predicate rolePredicate = criteriaBuilder.disjunction();
                    for (String role : roles) {
                        rolePredicate = criteriaBuilder.or(rolePredicate, criteriaBuilder.like(root.get("roles"), "%" + role + "%"));
                    }
                    predication = criteriaBuilder.and(predication, rolePredicate);
                }

                if (key.equals("iin") && StringUtil.isNotEmpty((String) value) && person.isStaff()) {
                    Predicate IINPredication = criteriaBuilder.or(
                            criteriaBuilder.like(root.get("information").get("IIN"), "%" + value + "%")
                    );
                    predication = criteriaBuilder.and(predication, IINPredication);
                }

                if (key.equals("phone") && StringUtil.isNotEmpty((String) value) && person.isStaff()) {
                    Predicate phonePredication = criteriaBuilder.or(
                        criteriaBuilder.like(root.get("information").get("phoneNumber"), "%" + value + "%")
                    );
                    predication = criteriaBuilder.and(predication, phonePredication);
                }

                if (key.equals("city") && ((Integer) value) > 0 && person.isStaff()) {
                    Join<PersonInformation, City> cityJoin = root.join("information").join("city", JoinType.INNER);
                    predication = criteriaBuilder.and(predication, criteriaBuilder.equal(cityJoin.get("ID"), value));
                }

                if (key.equals("nationality") && ((Integer) value) > 0 && person.isStaff()) {
                    Join<Person, Nationality> nationalityJoin = root.join("information").join("nationality", JoinType.INNER);
                    predication = criteriaBuilder.and(predication, criteriaBuilder.equal(nationalityJoin.get("ID"), value));
                }
            }
            return predication;
        });
    }
}
