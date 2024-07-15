package kz.tempest.tpapp.commons.repositories;

import kz.tempest.tpapp.commons.models.IFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IFileRepository extends JpaRepository<IFile, Long> {
    Optional<IFile> findById(Long id);
}
