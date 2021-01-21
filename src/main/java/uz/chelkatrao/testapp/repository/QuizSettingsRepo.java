package uz.chelkatrao.testapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.chelkatrao.testapp.domain.QuizSettings;

@Repository
public interface QuizSettingsRepo extends JpaRepository<QuizSettings, Long> {
}
