package uz.chelkatrao.testapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.chelkatrao.testapp.domain.QuizTitlePage;

@Repository
public interface QuizTitlePageRepo extends JpaRepository<QuizTitlePage, Long> {
    QuizTitlePage findByQuiz(Long quizId);
}
