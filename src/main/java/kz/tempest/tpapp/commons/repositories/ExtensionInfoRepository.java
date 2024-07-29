package kz.tempest.tpapp.commons.repositories;

import kz.tempest.tpapp.commons.enums.Extension;
import kz.tempest.tpapp.commons.models.ExtensionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtensionInfoRepository extends JpaRepository<ExtensionInfo, Extension> {
}
