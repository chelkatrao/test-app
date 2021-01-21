package uz.chelkatrao.testapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.chelkatrao.testapp.domain.*;
import uz.chelkatrao.testapp.repository.ProjectRepo;
import uz.chelkatrao.testapp.repository.QuizRepo;
import uz.chelkatrao.testapp.security.SecurityUtils;
import uz.chelkatrao.testapp.service.dto.quiz.QuizCreateDTO;

import java.util.Optional;

@Service
public class QuizService {

    private final QuizRepo quizRepo;
    private final ProjectRepo projectRepo;

    @Autowired
    public QuizService(QuizRepo quizRepo,
                       ProjectRepo projectRepo) {
        this.quizRepo = quizRepo;
        this.projectRepo = projectRepo;
    }

    public QuizCreateDTO createNewOrUpdateQuiz(QuizCreateDTO quizCreateDTO) {
        Quiz quiz;
        if (quizCreateDTO.getId() == null) {
            quiz = new Quiz();
        } else {
            quiz = quizRepo.findById(quizCreateDTO.getId()).orElseThrow(RuntimeException::new);
        }
        quiz.setName(quizCreateDTO.getName());
        quiz.setDescription(quizCreateDTO.getDescription());
        quiz.setCreatedBy(SecurityUtils.getCurrentUserLogin().orElseThrow(RuntimeException::new));
        quiz.setLastModifiedBy(SecurityUtils.getCurrentUserLogin().orElseThrow(RuntimeException::new));
        if (quizCreateDTO.getProjectId() != null) {
            Project project = projectRepo.findById(quizCreateDTO.getProjectId()).orElseThrow(RuntimeException::new);
            quiz.setProject(project);
        }
        return new QuizCreateDTO(quizRepo.save(quiz));
    }

    public Quiz getQuizById(Long id) {
        Optional<Quiz> quizOptional = quizRepo.findById(id);
        return quizOptional.orElse(null);
    }

    public void addTitleToQuiz(QuizTitlePage titlePage) {
        Optional<Quiz> quizOptional = quizRepo.findById(titlePage.getQuiz().getId());
        if (quizOptional.isPresent()) {
            Quiz quiz = quizOptional.get();
            quiz.setQuizTitlePage(titlePage);
            quizRepo.save(quiz);
        }
    }

    public void addSettingToQuiz(QuizSettings quizSettings, Long quizId) {
        Optional<Quiz> quizOptional = quizRepo.findById(quizId);
        if (quizOptional.isPresent()) {
            Quiz quiz = quizOptional.get();
            quiz.setQuizSettings(quizSettings);
            quizRepo.save(quiz);
        }
    }

    public void addResultScreen(QuizResultsScreen quizResultsScreen, Long quizId) {
        Optional<Quiz> quizOptional = quizRepo.findById(quizId);
        if (quizOptional.isPresent()) {
            Quiz quiz = quizOptional.get();
            quiz.setQuizResultsScreen(quizResultsScreen);
            quizRepo.save(quiz);
        }
    }
}
