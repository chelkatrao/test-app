package uz.chelkatrao.testapp.service;

import org.springframework.stereotype.Service;
import uz.chelkatrao.testapp.domain.QuizResultsScreen;
import uz.chelkatrao.testapp.repository.QuizResultsScreenRepo;
import uz.chelkatrao.testapp.service.dto.quiz.QuizResultsScreenDTO;

@Service
public class QuizResultsScreenService {

    private final QuizResultsScreenRepo quizResultsScreenRepo;

    public QuizResultsScreenService(QuizResultsScreenRepo quizResultsScreenRepo) {
        this.quizResultsScreenRepo = quizResultsScreenRepo;
    }

    public QuizResultsScreen createOrUpdateQuizResultsScreen(QuizResultsScreenDTO quizResultsScreenDTO) {
        QuizResultsScreen quizResultsScreen;
        if (quizResultsScreenDTO.getId() == null) {
            quizResultsScreen = new QuizResultsScreen();
        } else {
            quizResultsScreen = quizResultsScreenRepo.findById(quizResultsScreenDTO.getId()).orElseThrow(RuntimeException::new);
        }
        quizResultsScreen.setResultShow(quizResultsScreenDTO.getResultShow());
        quizResultsScreen.setResultNotSuccessDescription(quizResultsScreen.getResultSuccessDescription());
        quizResultsScreen.setResultNotSuccessMsg(quizResultsScreen.getResultSuccessMsg());
        quizResultsScreen.setResultSuccessDescription(quizResultsScreenDTO.getResultSuccessDescription());
        quizResultsScreen.setResultSuccessMsg(quizResultsScreenDTO.getResultSuccessMsg());
        quizResultsScreen.setTestCompletedDescription(quizResultsScreenDTO.getTestCompletedDescription());
        quizResultsScreen.setTestCompletedMsg(quizResultsScreenDTO.getTestCompletedMsg());
        return quizResultsScreenRepo.save(quizResultsScreen);
    }

}
