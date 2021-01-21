package uz.chelkatrao.testapp.web.rest;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.chelkatrao.testapp.domain.FileStorage;
import uz.chelkatrao.testapp.domain.Question.QuestionType;
import uz.chelkatrao.testapp.repository.FileStorageRepo;
import uz.chelkatrao.testapp.service.QuestionService;
import uz.chelkatrao.testapp.service.dto.question.QuestionDTO;
import uz.chelkatrao.testapp.service.dto.question.QuestionView;
import uz.chelkatrao.testapp.service.filestore.FileStorageService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/question")
public class QuestionResource {

    private final QuestionService questionService;
    private final FileStorageRepo fileStorageRepo;
    private final FileStorageService fileStorageService;

    public QuestionResource(QuestionService questionService,
                            FileStorageRepo fileStorageRepo,
                            FileStorageService fileStorageService) {
        this.questionService = questionService;
        this.fileStorageRepo = fileStorageRepo;
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/create-or-update")
    public ResponseEntity<QuestionView> createQuestion(@RequestParam(value = "id", required = false) Long id,
                                                       @RequestParam("questionText") String questionText,
                                                       @RequestParam("questionType") QuestionType questionType,
                                                       @RequestParam("quizId") Long quizId,
                                                       @RequestParam MultipartFile file) {
        return ResponseEntity.ok(questionService.createQuestion(
                new QuestionDTO(id, questionText, questionType, null, quizId), file));
    }

    @GetMapping("/download/photo/{fileName}")
    public ResponseEntity<Resource> downloadQuestionPhoto(@PathVariable String fileName, HttpServletRequest request) {
        FileStorage fileStorage = fileStorageRepo.findByHashId(fileName);
        String extension = fileStorage.getExtension();
        Resource resource = fileStorageService.downloadFile(fileStorage.getUploadPath(), fileName + '.' + extension);
        return fileStorageService.downloadPreparer(request, resource);
    }

}
