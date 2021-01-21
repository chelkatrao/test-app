package uz.chelkatrao.testapp.service.dto.question;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.chelkatrao.testapp.domain.Question;
import uz.chelkatrao.testapp.service.dto.AnswerDTO;
import uz.chelkatrao.testapp.service.dto.base.DTO;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDTO implements DTO {

    private Long id;
    private String questionText;
    private Question.QuestionType questionType;
    private Set<AnswerDTO> answerDTOs;
    private Long quizId;

    public QuestionDTO(Question model) {
        this.id = model.getId();
        this.questionText = model.getQuestionText();
        this.questionType = model.getQuestionType();

        if (Objects.nonNull(model.getAnswers())) {
            this.answerDTOs = model.getAnswers().stream()
                    .filter(Objects::nonNull)
                    .map(AnswerDTO::new)
                    .collect(Collectors.toSet());
        }
    }
}
