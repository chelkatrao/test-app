package uz.chelkatrao.testapp.domain;

import lombok.Getter;
import lombok.Setter;
import uz.chelkatrao.testapp.domain.base.AbstractAuditingEntity;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "q_answer")
public class Answer extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "answerSequenceGenerator")
    @SequenceGenerator(name = "answerSequenceGenerator", allocationSize = 1)
    private Long id;

    @Column(name = "answer_text", length = 10000, columnDefinition = " text ", nullable = false)
    private String answerText;

    @Column(name = "isCorrect", length = 10000, columnDefinition = " text ")
    private String isCorrect;

    @Column(name = "correct_feedback", length = 500)
    private String correctFeedback;

    @Column(name = "incorrect_feedback", length = 500)
    private String incorrectFeedback;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

}
