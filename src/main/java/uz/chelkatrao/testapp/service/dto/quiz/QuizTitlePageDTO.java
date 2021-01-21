package uz.chelkatrao.testapp.service.dto.quiz;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import uz.chelkatrao.testapp.domain.QuizTitlePage;
import uz.chelkatrao.testapp.service.dto.base.DTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizTitlePageDTO implements DTO {

    private Long id;
    private String title;
    private String description;
    private Long quizId;

    public QuizTitlePageDTO(QuizTitlePage quizTitlePage) {
        this.id = quizTitlePage.getId();
        this.title = quizTitlePage.getTitle();
        this.description = quizTitlePage.getDescription();
        if (quizTitlePage.getQuiz() != null) {
            this.quizId = quizTitlePage.getQuiz().getId();
        }
    }
}
