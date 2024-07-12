package kz.tempest.tpapp.modules.data.repositories;

import kz.tempest.tpapp.modules.data.models.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UniversityRepository extends JpaRepository<University, Long> {
}
