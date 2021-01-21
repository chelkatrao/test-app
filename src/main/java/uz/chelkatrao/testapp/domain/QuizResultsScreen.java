package uz.chelkatrao.testapp.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "q_quiz_results_screen")
public class QuizResultsScreen {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "quizSettingsSequenceGenerator")
    @SequenceGenerator(name = "quizSettingsSequenceGenerator", allocationSize = 1)
    private Long id;

    @Column(name = "result_show")
    @Enumerated(EnumType.STRING)
    private ResultShow resultShow = ResultShow.SHOW_RESULTS;

    @Column(name = "result_success_msg")
    private String resultSuccessMsg;

    @Column(name = "result_success_description")
    private String resultSuccessDescription;

    @Column(name = "result_not_success_msg")
    private String resultNotSuccessMsg;

    @Column(name = "result_not_success_description")
    private String resultNotSuccessDescription;

    @Column(name = "test_complated_msg")
    private String testCompletedMsg;

    @Column(name = "test_complated_description")
    private String testCompletedDescription;

    public enum ResultShow {
        SHOW_RESULTS, NOT_SHOW_RESULTS
    }

}
