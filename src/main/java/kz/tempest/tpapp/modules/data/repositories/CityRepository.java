package kz.tempest.tpapp.modules.data.repositories;

import kz.tempest.tpapp.modules.data.models.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
}
