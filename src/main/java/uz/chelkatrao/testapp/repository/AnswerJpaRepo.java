package uz.chelkatrao.testapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.chelkatrao.testapp.domain.Answer;

@Repository
public interface AnswerJpaRepo extends JpaRepository<Answer, Long> {
}
