package kz.tempest.tpapp.commons.repositories;

import kz.tempest.tpapp.commons.enums.Module;
import kz.tempest.tpapp.commons.models.ModuleInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleRepository extends JpaRepository<ModuleInfo, Module> {
}
