package uz.chelkatrao.testapp.service.dto.quiz;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.chelkatrao.testapp.domain.Quiz;
import uz.chelkatrao.testapp.service.dto.base.DTO;

@Getter
@Setter
@NoArgsConstructor
public class QuizCreateDTO implements DTO {
    private Long id;
    private String name;
    private String description;
    private Long projectId;

    public QuizCreateDTO(Quiz quiz) {
        this.id = quiz.getId();
        this.description = quiz.getDescription();
        this.name = quiz.getName();
        if (quiz.getProject() != null) {
            this.projectId = quiz.getProject().getId();
        }
    }
}
