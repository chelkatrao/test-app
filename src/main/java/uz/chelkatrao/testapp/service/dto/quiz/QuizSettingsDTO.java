package uz.chelkatrao.testapp.service.dto.quiz;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.chelkatrao.testapp.domain.QuizSettings;
import uz.chelkatrao.testapp.service.dto.base.DTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizSettingsDTO implements DTO {

    private Long id;
    private int numberOfAttempts;
    private int passingScore;
    private boolean timeLimit;
    private int timeToPassTheTest;
    private boolean shuffleTestQuestions;
    private boolean mixUpTheAnswer;
    private boolean showTheCorrectAnswer;
    private Long quizId;

    public QuizSettingsDTO(QuizSettings quizSettings) {
        this.id = quizSettings.getId();
        this.mixUpTheAnswer = quizSettings.isMixUpTheAnswer();
        this.numberOfAttempts = quizSettings.getNumberOfAttempts();
        this.passingScore = quizSettings.getPassingScore();
        this.showTheCorrectAnswer = quizSettings.isShowTheCorrectAnswer();
        this.shuffleTestQuestions = quizSettings.isShuffleTestQuestions();
        this.timeLimit = quizSettings.isTimeLimit();
        this.timeToPassTheTest = quizSettings.getTimeToPassTheTest();
    }

    public void setTimeLimit(boolean timeLimit) {
        if (!timeLimit) {
            timeToPassTheTest = 0;
        }
        this.timeLimit = timeLimit;
    }
}
