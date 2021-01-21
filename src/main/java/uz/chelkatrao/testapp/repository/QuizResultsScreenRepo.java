package uz.chelkatrao.testapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.chelkatrao.testapp.domain.QuizResultsScreen;

@Repository
public interface QuizResultsScreenRepo extends JpaRepository<QuizResultsScreen, Long> {
}
