package uz.chelkatrao.testapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.chelkatrao.testapp.domain.FileStorage;

@Repository
public interface FileStorageRepo extends JpaRepository<FileStorage, Long> {
    FileStorage findByHashId(String hashId);
}
