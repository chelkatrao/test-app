package uz.chelkatrao.testapp.web.rest.quiz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.chelkatrao.testapp.service.QuizService;
import uz.chelkatrao.testapp.service.dto.quiz.QuizCreateDTO;
import uz.chelkatrao.testapp.service.dto.quiz.QuizResultsScreenDTO;
import uz.chelkatrao.testapp.service.dto.quiz.QuizSettingsDTO;
import uz.chelkatrao.testapp.service.dto.quiz.QuizTitlePageDTO;

import static uz.chelkatrao.testapp.domain.QuizResultsScreen.ResultShow.NOT_SHOW_RESULTS;

@RestController
@RequestMapping("/api/quiz")
public class QuizResource {

    private final QuizService quizService;
    private final QuizScreenResultResource quizScreenResultResource;
    private final QuizSettingsResource quizSettingsResource;
    private final QuizTitlePageResource quizTitlePageResource;

    @Autowired
    public QuizResource(QuizService quizService,
                        QuizScreenResultResource quizScreenResultResource,
                        QuizSettingsResource quizSettingsResource,
                        QuizTitlePageResource quizTitlePageResource) {
        this.quizService = quizService;
        this.quizScreenResultResource = quizScreenResultResource;
        this.quizSettingsResource = quizSettingsResource;
        this.quizTitlePageResource = quizTitlePageResource;
    }

    @PostMapping("/new")
    public ResponseEntity<QuizCreateDTO> createNewQuiz(@RequestBody QuizCreateDTO quizCreateDTO) {
        QuizCreateDTO newQuiz = quizService.createNewOrUpdateQuiz(quizCreateDTO);
        QuizSettingsDTO quizCustomSettings = new QuizSettingsDTO();
        quizCustomSettings.setQuizId(newQuiz.getId());
        quizCustomSettings.setTimeLimit(true);
        quizCustomSettings.setTimeToPassTheTest(5);
        quizCustomSettings.setMixUpTheAnswer(true);
        quizCustomSettings.setPassingScore(80);
        quizCustomSettings.setNumberOfAttempts(1);
        quizCustomSettings.setShowTheCorrectAnswer(true);
        quizCustomSettings.setShuffleTestQuestions(true);
        quizSettingsResource.addQuizSettings(quizCustomSettings);

        QuizTitlePageDTO quizCustomTitlePageDTO = new QuizTitlePageDTO();
        quizCustomTitlePageDTO.setQuizId(newQuiz.getId());
        quizCustomTitlePageDTO.setDescription("Новый тест");
        quizCustomTitlePageDTO.setTitle("Новый тест");
        quizTitlePageResource.addQuizTitlePage(quizCustomTitlePageDTO, null);

        QuizResultsScreenDTO quizResultsScreenDTO = new QuizResultsScreenDTO();
        quizResultsScreenDTO.setQuizId(newQuiz.getId());
        quizResultsScreenDTO.setResultShow(NOT_SHOW_RESULTS);
        quizResultsScreenDTO.setTestCompletedMsg("Спасибо за прохождение теста.");
        quizResultsScreenDTO.setTestCompletedDescription("Спасибо за прохождение теста.");
        quizScreenResultResource.addQuizScreenResult(quizResultsScreenDTO);
        return ResponseEntity.ok(newQuiz);
    }

    @PutMapping("/update")
    public ResponseEntity<QuizCreateDTO> updateQuiz(@RequestBody QuizCreateDTO quizCreateDTO) {
        QuizCreateDTO newOrUpdateQuiz = quizService.createNewOrUpdateQuiz(quizCreateDTO);
        return ResponseEntity.ok(newOrUpdateQuiz);
    }
    //TODO: Quizni ayni payda nechta odam yechayotganini ko'rsatish
}
