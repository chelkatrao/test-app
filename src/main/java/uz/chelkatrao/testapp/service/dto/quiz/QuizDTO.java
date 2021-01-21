package uz.chelkatrao.testapp.service.dto.quiz;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.chelkatrao.testapp.domain.Quiz;
import uz.chelkatrao.testapp.service.dto.base.DTO;
import uz.chelkatrao.testapp.service.dto.question.QuestionDTO;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class QuizDTO implements DTO {
    private Long id;
    private String name;
    private String description;
    private QuizTitlePageDTO quizTitlePageDTO;
    private Set<QuestionDTO> questions;
    private QuizSettingsDTO quizSettingsDTO;
    private Long projectId;

    public QuizDTO(Quiz quiz) {
        this.id = quiz.getId();
        this.description = quiz.getDescription();
        this.name = quiz.getName();

        if (Objects.nonNull(quiz.getQuizTitlePage())) {
            this.quizTitlePageDTO = new QuizTitlePageDTO(quiz.getQuizTitlePage());
        }

        if (Objects.nonNull(quiz.getProject())) {
            this.projectId = quiz.getProject().getId();
        }

        this.questions = quiz.getQuestions().stream()
                .filter(Objects::nonNull)
                .map(QuestionDTO::new)
                .collect(Collectors.toSet());
    }
}
