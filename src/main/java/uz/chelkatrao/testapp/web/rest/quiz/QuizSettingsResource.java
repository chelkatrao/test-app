package uz.chelkatrao.testapp.web.rest.quiz;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.chelkatrao.testapp.domain.Quiz;
import uz.chelkatrao.testapp.domain.QuizSettings;
import uz.chelkatrao.testapp.service.QuizService;
import uz.chelkatrao.testapp.service.QuizSettingsService;
import uz.chelkatrao.testapp.service.dto.quiz.QuizSettingsDTO;

@RestController
@RequestMapping("/api/quiz-settings")
public class QuizSettingsResource {

    private final QuizSettingsService quizSettingsService;
    private final QuizService quizService;

    public QuizSettingsResource(QuizSettingsService quizSettingsService,
                                QuizService quizService) {
        this.quizSettingsService = quizSettingsService;
        this.quizService = quizService;
    }

    @PostMapping("/create")
    public ResponseEntity<QuizSettings> addQuizSettings(@RequestBody QuizSettingsDTO settingsDTO) {
        QuizSettings quizSettings = quizSettingsService.createOrUpdateQuizSettings(settingsDTO);
        quizService.addSettingToQuiz(quizSettings, settingsDTO.getQuizId());
        return ResponseEntity.ok(quizSettings);
    }

    @GetMapping("/get/{quizId}")
    public ResponseEntity<QuizSettings> getSettingsByQuizId(@PathVariable("quizId") Long quizId) {
        Quiz quizById = quizService.getQuizById(quizId);
        return ResponseEntity.ok(quizById.getQuizSettings());
    }

    @PutMapping("/update")
    public ResponseEntity<QuizSettings> updateQuizSettings(@RequestBody QuizSettingsDTO quizSettingsDTO) {
        QuizSettings orUpdateQuizTitlePage = quizSettingsService.createOrUpdateQuizSettings(quizSettingsDTO);
        return ResponseEntity.ok(orUpdateQuizTitlePage);
    }
}
