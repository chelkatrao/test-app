package uz.chelkatrao.testapp.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.chelkatrao.testapp.domain.Project;
import uz.chelkatrao.testapp.service.dto.base.DTO;
import uz.chelkatrao.testapp.service.dto.quiz.QuizDTO;

import java.time.Instant;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class ProjectDTO implements DTO {

    private Long id;
    private String name;
    private String description;
    private Set<QuizDTO> quizDTOs;
    private Instant createdDate;

    public ProjectDTO(Project project) {
        this.id = project.getId();
        this.name = project.getName();
        this.description = project.getDescription();
        if (Objects.nonNull(project.getQuizzes())) {
            this.quizDTOs = project.getQuizzes().stream()
                    .filter(Objects::nonNull)
                    .map(QuizDTO::new)
                    .collect(Collectors.toSet());
        }
    }

}
