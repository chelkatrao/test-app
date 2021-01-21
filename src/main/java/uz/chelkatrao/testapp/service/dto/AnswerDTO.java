package uz.chelkatrao.testapp.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.chelkatrao.testapp.domain.Answer;
import uz.chelkatrao.testapp.service.dto.base.DTO;

@Getter
@Setter
@NoArgsConstructor
public class AnswerDTO implements DTO {
    private Long id;
    private String answerText;
    private String isCorrect;
    private String correctFeedback;
    private String incorrectFeedback;

    public AnswerDTO(Answer answer) {
        this.id = answer.getId();
        this.answerText = answer.getAnswerText();
        this.isCorrect = answer.getIsCorrect();
        this.correctFeedback = answer.getIncorrectFeedback();
        this.incorrectFeedback = answer.getIncorrectFeedback();
    }
}
