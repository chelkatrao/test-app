package uz.chelkatrao.testapp.domain;

import lombok.Getter;
import lombok.Setter;
import uz.chelkatrao.testapp.domain.base.AbstractAuditingEntity;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "q_question")
public class Question extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "questionSequenceGenerator")
    @SequenceGenerator(name = "questionSequenceGenerator", allocationSize = 1)
    private Long id;

    @Column(name = "question_text", length = 10000, columnDefinition = " text ", nullable = false)
    private String questionText;

    @Column(name = "question_type")
    @Enumerated(EnumType.STRING)
    private QuestionType questionType = QuestionType.ONE_ANSWER;

    @OneToOne
    @JoinColumn(name = "fileStorage")
    private FileStorage fileStorage;

    @OneToMany(mappedBy = "question")
    private Set<Answer> answers;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    public enum QuestionType {
        ONE_ANSWER, MULTIPLE_ANSWERS, SHORT_ANSWER
    }
}
