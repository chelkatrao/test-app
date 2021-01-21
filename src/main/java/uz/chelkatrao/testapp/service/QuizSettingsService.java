package uz.chelkatrao.testapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.chelkatrao.testapp.domain.QuizSettings;
import uz.chelkatrao.testapp.repository.QuizSettingsRepo;
import uz.chelkatrao.testapp.service.dto.quiz.QuizSettingsDTO;

@Service
public class QuizSettingsService {

    private final QuizSettingsRepo quizSettingsRepo;

    @Autowired
    public QuizSettingsService(QuizSettingsRepo quizSettingsRepo) {
        this.quizSettingsRepo = quizSettingsRepo;
    }

    public QuizSettings createOrUpdateQuizSettings(QuizSettingsDTO settingsDTO) {
        QuizSettings quizSettings;
        if (settingsDTO.getId() == null) {
            quizSettings = new QuizSettings();
        } else {
            quizSettings = quizSettingsRepo.findById(settingsDTO.getId()).orElseThrow(RuntimeException::new);
        }
        quizSettings.setMixUpTheAnswer(settingsDTO.isMixUpTheAnswer());
        quizSettings.setPassingScore(settingsDTO.getPassingScore());
        quizSettings.setShowTheCorrectAnswer(settingsDTO.isShowTheCorrectAnswer());
        quizSettings.setTimeToPassTheTest(settingsDTO.getTimeToPassTheTest());
        quizSettings.setTimeLimit(settingsDTO.isTimeLimit());
        quizSettings.setShuffleTestQuestions(settingsDTO.isShuffleTestQuestions());
        quizSettings.setNumberOfAttempts(settingsDTO.getNumberOfAttempts());
        return quizSettingsRepo.save(quizSettings);
    }
}
