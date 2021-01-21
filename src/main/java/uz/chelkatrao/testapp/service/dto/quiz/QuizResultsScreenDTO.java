package uz.chelkatrao.testapp.service.dto.quiz;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.chelkatrao.testapp.domain.QuizResultsScreen.ResultShow;
import uz.chelkatrao.testapp.service.dto.base.DTO;

@Getter
@Setter
@NoArgsConstructor
public class QuizResultsScreenDTO implements DTO {
    private Long id;
    private ResultShow resultShow;
    private String resultSuccessMsg;
    private String resultSuccessDescription;
    private String resultNotSuccessMsg;
    private String resultNotSuccessDescription;
    private String testCompletedMsg;
    private String testCompletedDescription;
    private Long quizId;
}
