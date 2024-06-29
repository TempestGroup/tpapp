package kz.tempest.tpapp.commons.repositories;

import kz.tempest.tpapp.commons.models.LogInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogInfoRepository extends JpaRepository<LogInfo, Long> {}
