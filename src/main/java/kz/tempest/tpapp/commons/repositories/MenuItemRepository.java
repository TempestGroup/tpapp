package kz.tempest.tpapp.commons.repositories;

import kz.tempest.tpapp.commons.enums.Module;
import kz.tempest.tpapp.commons.models.MenuItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    Page<MenuItem> findAll(Specification<MenuItem> specification, Pageable pageable);
    List<MenuItem> findAllByModuleIn(List<Module> modules);
}
