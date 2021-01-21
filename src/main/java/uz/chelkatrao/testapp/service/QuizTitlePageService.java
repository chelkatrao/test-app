package uz.chelkatrao.testapp.service;

import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uz.chelkatrao.testapp.domain.FileStorage;
import uz.chelkatrao.testapp.domain.QuizTitlePage;
import uz.chelkatrao.testapp.repository.FileStorageRepo;
import uz.chelkatrao.testapp.repository.QuizTitlePageRepo;
import uz.chelkatrao.testapp.security.SecurityUtils;
import uz.chelkatrao.testapp.service.dto.quiz.QuizTitlePageDTO;
import uz.chelkatrao.testapp.service.dto.quiz.QuizTitlePageView;
import uz.chelkatrao.testapp.service.filestore.FileStorageService;

@Service
public class QuizTitlePageService {

    private final QuizTitlePageRepo quizTitlePageRepo;
    private final QuizService quizService;
    private final FileStorageService fileStorageService;
    private final FileStorageRepo fileStorageRepo;
    private final Hashids hashIds;

    @Autowired
    public QuizTitlePageService(QuizTitlePageRepo quizTitlePageRepo,
                                QuizService quizService,
                                FileStorageService fileStorageService, FileStorageRepo fileStorageRepo) {
        this.quizTitlePageRepo = quizTitlePageRepo;
        this.quizService = quizService;
        this.fileStorageService = fileStorageService;
        this.fileStorageRepo = fileStorageRepo;
        hashIds = new Hashids(getClass().getName(), 20);
    }

    public QuizTitlePageView createOrUpdateQuizTitlePage(QuizTitlePageDTO quizTitlePageDTO, MultipartFile file) {
        QuizTitlePage quizTitlePage;
        if (quizTitlePageDTO.getId() == null) {
            quizTitlePage = new QuizTitlePage();
        } else {
            quizTitlePage = quizTitlePageRepo.findById(quizTitlePageDTO.getId()).orElseThrow(RuntimeException::new);
        }
        quizTitlePage.setTitle(quizTitlePageDTO.getTitle());
        quizTitlePage.setQuiz(quizService.getQuizById(quizTitlePageDTO.getQuizId()));
        quizTitlePage.setDescription(quizTitlePageDTO.getDescription());
        quizTitlePage.setLastModifiedBy(SecurityUtils.getCurrentUserLogin()
                .orElseThrow(RuntimeException::new));
        quizTitlePage.setCreatedBy(SecurityUtils.getCurrentUserLogin()
                .orElseThrow(RuntimeException::new));

        quizService.addTitleToQuiz(quizTitlePage);
        String downloadUrl = null;
        if (file != null) {
            FileStorage fileStorage = storeTitleFile(quizTitlePage, file);

            downloadUrl = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/quiz-title-page/download/photo/")
                    .path(fileStorage.getHashId())
                    .toUriString();
        }

        QuizTitlePageView view = new QuizTitlePageView(quizTitlePageRepo.save(quizTitlePage));
        view.setDownloadPath(downloadUrl);
        return view;
    }

    private FileStorage storeTitleFile(QuizTitlePage quizTitlePage, MultipartFile multipartFile) {
        FileStorage quizTitleStoreFile = fileStorageService.multipartToFileStorage(multipartFile);

        FileStorage saveTitlePageStoreFile = fileStorageRepo.save(quizTitleStoreFile);
        saveTitlePageStoreFile.setHashId(hashIds.encode(saveTitlePageStoreFile.getId()));
        fileStorageService.storeQuizTitlePhoto(multipartFile, saveTitlePageStoreFile);

        fileStorageRepo.save(saveTitlePageStoreFile);
        quizTitlePage.setFileStorage(quizTitleStoreFile);
        return quizTitleStoreFile;
    }

    public QuizTitlePageView findQuizTitlePageById(Long quizId) {
        QuizTitlePage quizTitlePage = quizTitlePageRepo.findByQuiz(quizId);
        FileStorage fileStorage = quizTitlePage.getFileStorage();
        QuizTitlePageView quizTitlePageView = new QuizTitlePageView(quizTitlePage);

        if (quizTitlePage.getFileStorage() != null) {
            String downloadUrl = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/quiz-title-page/download/photo/")
                    .path(fileStorage.getHashId())
                    .toUriString();
            quizTitlePageView.setDownloadPath(downloadUrl);
        }
        return quizTitlePageView;
    }

}
