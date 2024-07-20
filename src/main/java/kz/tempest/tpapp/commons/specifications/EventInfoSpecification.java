package kz.tempest.tpapp.commons.specifications;

import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import kz.tempest.tpapp.commons.enums.EventType;
import kz.tempest.tpapp.commons.enums.Module;
import kz.tempest.tpapp.commons.models.EventInfo;
import kz.tempest.tpapp.commons.utils.StringUtil;
import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDateTime;
import java.util.Map;

public class EventInfoSpecification {
    public static Specification<EventInfo> getSpecification(Map<String, Object> filter){
        return ((root, query, criteriaBuilder) -> {
            Predicate predication = criteriaBuilder.conjunction();
            for (String key: filter.keySet()) {
                Object value = filter.get(key);
                if (key.equals("name") && StringUtil.isNotEmpty((String) value)) {
                    predication = criteriaBuilder.or(
                            criteriaBuilder.like(root.get("contentKK"), "%" + (String) value + "%"),
                            criteriaBuilder.like(root.get("contentRU"), "%" + (String) value + "%"),
                            criteriaBuilder.like(root.get("contentEN"), "%" + (String) value + "%")
                    );
                }

                if (key.equals("module") && value instanceof String && !((String) value).isEmpty() && Module.contains((String) value)) {
                    Module module = Module.getByCode((String) value);
                    Path<String> modulePath = root.join("module");
                    Predicate modulePredicate = modulePath.in(module);
                    predication = criteriaBuilder.and(predication, modulePredicate);
                }

                if (key.equals("type") && value instanceof String && !((String) value).isEmpty() && EventType.contains((String) value)) {
                    EventType eventType = EventType.getByCode((String) value);
                    Path<String> eventTypePath = root.join("type");
                    Predicate eventTypePredicate = eventTypePath.in(eventType);
                    predication = criteriaBuilder.and(predication, eventTypePredicate);
                }

                if (key.equals("startDate") && value instanceof String && !((String) value).isEmpty()) {
                    LocalDateTime startDate = LocalDateTime.parse((String) value);
                    Predicate startDatePredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("time"), startDate);
                    predication = criteriaBuilder.and(predication, startDatePredicate);
                }

                if (key.equals("endDate") && value instanceof String && !((String) value).isEmpty()) {
                    LocalDateTime endDate = LocalDateTime.parse((String) value);
                    Predicate endDatePredicate = criteriaBuilder.lessThanOrEqualTo(root.get("time"), endDate);
                    predication = criteriaBuilder.and(predication, endDatePredicate);
                }
            }
            return predication;
        });
    }
}
