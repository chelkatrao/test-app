package uz.chelkatrao.testapp.domain;

import lombok.Getter;
import lombok.Setter;
import uz.chelkatrao.testapp.domain.base.AbstractAuditingEntity;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "t_quiz")
public class Quiz extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "quizSequenceGenerator")
    @SequenceGenerator(name = "quizSequenceGenerator", allocationSize = 1)
    private Long id;

    @Column(name = "name", length = 1000, nullable = false)
    private String name;

    @Column(name = "description", length = 3000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToMany(mappedBy = "quiz")
    private Set<Question> questions;

    @OneToOne
    @JoinColumn(name = "quiz_settings")
    private QuizSettings quizSettings;

    @OneToOne
    @JoinColumn(name = "title_page")
    private QuizTitlePage quizTitlePage;

    @OneToOne
    @JoinColumn(name = "quiz_result_screen")
    private QuizResultsScreen quizResultsScreen;

}
