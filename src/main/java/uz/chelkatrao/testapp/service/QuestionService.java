package uz.chelkatrao.testapp.service;

import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uz.chelkatrao.testapp.domain.FileStorage;
import uz.chelkatrao.testapp.domain.Question;
import uz.chelkatrao.testapp.repository.FileStorageRepo;
import uz.chelkatrao.testapp.repository.QuestionRepo;
import uz.chelkatrao.testapp.security.SecurityUtils;
import uz.chelkatrao.testapp.service.dto.question.QuestionDTO;
import uz.chelkatrao.testapp.service.dto.question.QuestionView;
import uz.chelkatrao.testapp.service.filestore.FileStorageService;

import java.util.Objects;

@Service
public class QuestionService {

    private final QuestionRepo questionRepo;
    private final FileStorageService fileStorageService;
    private final FileStorageRepo fileStorageRepo;
    private final Hashids hashIds;

    @Autowired
    public QuestionService(QuestionRepo questionRepo,
                           FileStorageService fileStorageService,
                           FileStorageRepo fileStorageRepo) {
        this.questionRepo = questionRepo;
        this.fileStorageService = fileStorageService;
        this.fileStorageRepo = fileStorageRepo;
        hashIds = new Hashids(getClass().getName(), 20);
    }

    public QuestionView createQuestion(QuestionDTO questionDTO, MultipartFile file) {
        Question question;
        if (questionDTO.getId() == null) {
            question = new Question();
        } else {
            question = questionRepo.findById(questionDTO.getId()).orElseThrow(RuntimeException::new);
        }
        question.setQuestionText(questionDTO.getQuestionText());
        question.setQuestionType(questionDTO.getQuestionType());
        question.setCreatedBy(SecurityUtils.getCurrentUserLogin().orElseThrow(RuntimeException::new));
        question.setLastModifiedBy(SecurityUtils.getCurrentUserLogin().orElseThrow(RuntimeException::new));

        String downloadUrl = null;
        if (file != null) {
            FileStorage fileStorage = storeQuestionFile(question, file);

            downloadUrl = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/question/download/photo/")
                    .path(fileStorage.getHashId())
                    .toUriString();
        }
        QuestionView questionView = new QuestionView(questionRepo.save(question));
        questionView.setDownloadPath(downloadUrl);
        return questionView;
    }

    private FileStorage storeQuestionFile(Question question, MultipartFile multipartFile) {
        FileStorage questionStoreFile = fileStorageService.multipartToFileStorage(multipartFile);

        FileStorage saveQuestionStoreFile = fileStorageRepo.save(questionStoreFile);
        saveQuestionStoreFile.setHashId(hashIds.encode(saveQuestionStoreFile.getId()));
        fileStorageService.storeQuestionPhotos(multipartFile, saveQuestionStoreFile);

        fileStorageRepo.save(saveQuestionStoreFile);
        question.setFileStorage(questionStoreFile);
        return questionStoreFile;
    }

}
