package uz.chelkatrao.testapp.service.dto.question;

import lombok.Getter;
import lombok.Setter;
import uz.chelkatrao.testapp.domain.Question;

@Getter
@Setter
public class QuestionView {
    private Long id;
    private String questionText;
    private Question.QuestionType questionType;
    private Long quizId;
    private String downloadPath;

    public QuestionView(Question question) {
        this.id = question.getId();
        this.questionText = question.getQuestionText();
        this.questionType = question.getQuestionType();
        if (question.getQuiz() != null) {
            this.quizId = question.getQuiz().getId();
        }
    }
}
