package uz.chelkatrao.testapp.service.dto.quiz;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.chelkatrao.testapp.domain.QuizTitlePage;

@Getter
@Setter
@NoArgsConstructor
public class QuizTitlePageView {

    private Long id;
    private String title;
    private String description;
    private String downloadPath;
    private Long quizId;

    public QuizTitlePageView(QuizTitlePage quizTitlePage) {
        this.id = quizTitlePage.getId();
        this.title = quizTitlePage.getTitle();
        this.description = quizTitlePage.getDescription();
        if (quizTitlePage.getQuiz() != null) {
            this.quizId = quizTitlePage.getQuiz().getId();
        }
    }
}
