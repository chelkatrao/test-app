package uz.chelkatrao.testapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.chelkatrao.testapp.domain.Question;

@Repository
public interface QuestionRepo extends JpaRepository<Question, Long> {
}
