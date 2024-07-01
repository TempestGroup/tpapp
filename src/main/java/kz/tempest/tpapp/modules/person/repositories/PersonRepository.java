package kz.tempest.tpapp.modules.person.repositories;

import kz.tempest.tpapp.modules.person.models.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    @EntityGraph(attributePaths = {"roles"})
    Optional<Person> findByEmail(String email);
    @EntityGraph(attributePaths = {"roles"})
    Page<Person> findAll(Specification<Person> specification, Pageable pageable);
}
