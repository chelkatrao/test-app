package uz.chelkatrao.testapp.web.rest.quiz;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.chelkatrao.testapp.domain.Quiz;
import uz.chelkatrao.testapp.domain.QuizResultsScreen;
import uz.chelkatrao.testapp.service.QuizResultsScreenService;
import uz.chelkatrao.testapp.service.QuizService;
import uz.chelkatrao.testapp.service.dto.quiz.QuizResultsScreenDTO;

@RestController
@RequestMapping("/api/screen-result")
public class QuizScreenResultResource {

    private final QuizService quizService;
    private final QuizResultsScreenService quizResultsScreenService;

    public QuizScreenResultResource(QuizService quizService,
                                    QuizResultsScreenService quizResultsScreenService) {
        this.quizService = quizService;
        this.quizResultsScreenService = quizResultsScreenService;
    }

    @PostMapping("/create")
    public ResponseEntity<QuizResultsScreen> addQuizScreenResult(QuizResultsScreenDTO quizResultsScreenDTO) {
        QuizResultsScreen quizResultsScreen = quizResultsScreenService.createOrUpdateQuizResultsScreen(quizResultsScreenDTO);
        quizService.addResultScreen(quizResultsScreen, quizResultsScreenDTO.getQuizId());
        return ResponseEntity.ok(quizResultsScreen);
    }

    @GetMapping("/get/{quizId}")
    public ResponseEntity<QuizResultsScreen> getScreenResultByQuizId(@PathVariable("quizId") Long quizId) {
        Quiz quizById = quizService.getQuizById(quizId);
        return ResponseEntity.ok(quizById.getQuizResultsScreen());
    }

    @PutMapping("/update")
    public ResponseEntity<QuizResultsScreen> updateQuizSettings(@RequestBody QuizResultsScreenDTO quizResultsScreenDTO) {
        QuizResultsScreen quizResultsScreen = quizResultsScreenService.createOrUpdateQuizResultsScreen(quizResultsScreenDTO);
        return ResponseEntity.ok(quizResultsScreen);
    }

}
