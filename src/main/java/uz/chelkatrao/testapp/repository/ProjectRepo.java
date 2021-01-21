package uz.chelkatrao.testapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.chelkatrao.testapp.domain.Project;

@Repository
public interface ProjectRepo extends JpaRepository<Project, Long> {
}
