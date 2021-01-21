package uz.chelkatrao.testapp.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "q_quiz_settings")
public class QuizSettings {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "quizSettingsSequenceGenerator")
    @SequenceGenerator(name = "quizSettingsSequenceGenerator", allocationSize = 1)
    private Long id;

    /**
     * Urinishlar soni
     */
    @Column(name = "number_of_attempts")
    private int numberOfAttempts;

    /**
     * O'tish bali
     */
    @Column(name = "passing_score")
    private int passingScore;

    /**
     * vaqt bilan chegaralash
     */
    @Column(name = "time_limit")
    private boolean timeLimit;

    /**
     * testdan o'tish vaqti
     */
    @Column(name = "time_to_pass_the_test")
    private int timeToPassTheTest;

    /**
     * savollarni aralashtirib berish
     */
    @Column(name = "shuffle_test_questions")
    private boolean shuffleTestQuestions;

    /**
     * Javoblarni aralashtirib berish
     */
    @Column(name = "mix_up_the_answer")
    private boolean mixUpTheAnswer;

    /**
     * To'g'ri javoblarni ko'rsatish
     */
    @Column(name = "show_the_correct_answer")
    private boolean showTheCorrectAnswer;

}
