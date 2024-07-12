package kz.tempest.tpapp.modules.data.repositories;

import kz.tempest.tpapp.modules.data.models.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
}
