package kz.tempest.tpapp.commons.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class DBUtil {

    private static EntityManager manager;

    public DBUtil(EntityManager manager) {
        DBUtil.manager = manager;
    }

    public static Session getSession() {
        return manager.unwrap(Session.class);
    }

//    public static <Entity, DTO> Page<DTO> findTuplePage(Class<Entity> entityClass, Class<DTO> mapClass, Specification<Entity> specification, Pageable pageable) {
//        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
//        CriteriaQuery<Tuple> query = criteriaBuilder.createTupleQuery();
//        Root<Entity> root = query.from(entityClass);
////        if () {
////
////        }
//    }
}
