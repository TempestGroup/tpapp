package kz.tempest.tpapp.commons.repositories;

import kz.tempest.tpapp.commons.models.EventInfo;
import kz.tempest.tpapp.modules.person.models.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventInfoRepository extends JpaRepository<EventInfo, Long> {
    Page<EventInfo> findAll(Specification<EventInfo> specification, Pageable pageable);
}
