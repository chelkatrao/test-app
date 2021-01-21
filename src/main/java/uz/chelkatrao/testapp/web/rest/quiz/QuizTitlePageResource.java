package uz.chelkatrao.testapp.web.rest.quiz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.chelkatrao.testapp.domain.FileStorage;
import uz.chelkatrao.testapp.repository.FileStorageRepo;
import uz.chelkatrao.testapp.service.QuizTitlePageService;
import uz.chelkatrao.testapp.service.dto.quiz.QuizTitlePageDTO;
import uz.chelkatrao.testapp.service.dto.quiz.QuizTitlePageView;
import uz.chelkatrao.testapp.service.filestore.FileStorageService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/quiz-title-page")
public class QuizTitlePageResource {

    private final QuizTitlePageService quizTitlePageService;
    private final FileStorageRepo fileStorageRepo;
    private final FileStorageService fileStorageService;

    @Autowired
    public QuizTitlePageResource(QuizTitlePageService quizTitlePageService,
                                 FileStorageRepo fileStorageRepo,
                                 FileStorageService fileStorageService) {
        this.quizTitlePageService = quizTitlePageService;
        this.fileStorageRepo = fileStorageRepo;
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/create")
    public ResponseEntity<QuizTitlePageView> addQuizTitlePage(@RequestBody QuizTitlePageDTO quizTitlePageDTO, @RequestParam MultipartFile file) {
        return ResponseEntity.ok(quizTitlePageService.createOrUpdateQuizTitlePage(quizTitlePageDTO, file));
    }

    @GetMapping("/get/{quizId}")
    public ResponseEntity<QuizTitlePageView> getQuizTitlePageByQuizId(@PathVariable("quizId") Long quizId) {
        return ResponseEntity.ok(quizTitlePageService.findQuizTitlePageById(quizId));
    }

    @PutMapping("/update")
    public ResponseEntity<QuizTitlePageView> updateQuizTitlePage(@RequestParam("id") Long id,
                                                                 @RequestParam("title") String title,
                                                                 @RequestParam("description") String description,
                                                                 @RequestParam("quizId") Long quizId,
                                                                 @RequestParam("file") MultipartFile file) {

        return ResponseEntity.ok(quizTitlePageService.createOrUpdateQuizTitlePage(
                        new QuizTitlePageDTO(id, title, description, quizId), file));
    }

    @GetMapping("/download/photo/{fileName}")
    public ResponseEntity<Resource> downloadQuestionPhoto(@PathVariable String fileName, HttpServletRequest request) {
        FileStorage fileStorage = fileStorageRepo.findByHashId(fileName);
        String extension = fileStorage.getExtension();
        Resource resource = fileStorageService.downloadFile(fileStorage.getUploadPath(), fileName + '.' + extension);
        return fileStorageService.downloadPreparer(request, resource);
    }

}
