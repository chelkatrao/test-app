package uz.chelkatrao.testapp.service.filestore;

import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.chelkatrao.testapp.domain.FileStorage;
import uz.chelkatrao.testapp.repository.FileStorageRepo;
import uz.chelkatrao.testapp.security.SecurityUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Objects;

@Service
public class FileStorageService {

    /**
     * Paths file
     */
    private final Path fileStorageQuizTitlesPhotosPath;
    private final Path fileStorageQuestionPhotosPath;
    private final FileStorageRepo fileStorageRepo;
    private final Hashids hashIds;

    /**
     * Created file storage directories
     *
     * @param fileStorageLocation files location directory
     */
    @Autowired
    public FileStorageService(@Value("${file.storage.location}") String fileStorageLocation,
                              @Value("${file.storage.location.quiz-title-photos}") String quizTitlePhotos,
                              @Value("${file.storage.location.question-photos}") String questionPhotos,
                              FileStorageRepo fileStorageRepo) {
        this.fileStorageRepo = fileStorageRepo;
        hashIds = new Hashids(getClass().getName(), 20);
        /*
         Path | 📁 storeDirectory
         Path | 📁 storeDirectory / 📁 quizTitlePhotos
         Path | 📁 storeDirectory / 📁 questionPhotos
         */
        Path fileStoragePath = Paths.get(fileStorageLocation).toAbsolutePath().normalize();
        this.fileStorageQuizTitlesPhotosPath = Paths.get(fileStorageLocation + '\\' + quizTitlePhotos).toAbsolutePath().normalize();
        this.fileStorageQuestionPhotosPath = Paths.get(fileStorageLocation + '\\' + questionPhotos).toAbsolutePath().normalize();

        try {
            Files.createDirectories(fileStoragePath);                  // 📁-> create root folder
            Files.createDirectories(fileStorageQuizTitlesPhotosPath);  // 📁-> create msg file folder
            Files.createDirectories(fileStorageQuestionPhotosPath);    // 📁-> create profile photo folder
        } catch (IOException e) {
            throw new RuntimeException("Issue in creating file directory");
        }
    }

    public void storeQuestionPhotos(MultipartFile file, FileStorage questionPhotosModel) {
        Calendar now = Calendar.getInstance();
        questionPhotosModel.setUploadPath(String.format("%s\\%d\\%d\\%d",
                fileStorageQuestionPhotosPath,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)));

        fileStorage(file, fileStorageQuestionPhotosPath, questionPhotosModel.getHashId() + "." + questionPhotosModel.getExtension());
    }

    /**
     * @param file                quiz title photo
     * @param quizTitlePhotoModel quiz title model
     */
    public void storeQuizTitlePhoto(MultipartFile file, FileStorage quizTitlePhotoModel) {
        Calendar now = Calendar.getInstance();
        quizTitlePhotoModel.setUploadPath(String.format("%s\\%d\\%d\\%d",
                fileStorageQuizTitlesPhotosPath,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)));

        fileStorage(file, fileStorageQuizTitlesPhotosPath, quizTitlePhotoModel.getHashId() + "." + quizTitlePhotoModel.getExtension());
    }

    private void fileStorage(MultipartFile file, Path fileStoragePath, String hashedFileName) {
        Path storagePath = createStoragePath(fileStoragePath);

        try {
            Files.createDirectories(storagePath);
        } catch (IOException e) {
            throw new RuntimeException("Issue in creating file directory");
        }

        try {
            Files.copy(file.getInputStream(), Paths.get(storagePath.toString(), hashedFileName));
        } catch (IOException e) {
            throw new RuntimeException("Issue in storing the file", e);
        }
    }

    private Path createStoragePath(Path fileStoragePath) {
        Calendar now = Calendar.getInstance();
        return Paths.get(String.format("%s\\%d\\%d\\%d",
                fileStoragePath,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH))
        ).toAbsolutePath();
    }

    public Resource downloadFile(String filePath, String fileName) {
        Path path = Paths.get(filePath).toAbsolutePath().resolve(fileName).normalize();
        Resource resource;
        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            throw new RuntimeException("Issue in reading file", e);
        }

        if (resource.exists() && resource.isReadable()) {
            return resource;
        } else {
            throw new RuntimeException("The file doesn't exist or readable");
        }
    }

    public ResponseEntity<Resource> downloadPreparer(HttpServletRequest request, Resource resource) {
        String mimeType;
        try {
            mimeType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + resource.getFilename())
                .body(resource);
    }

    public FileStorage  multipartToFileStorage(MultipartFile multipartFile) {
        FileStorage fileStorage = new FileStorage();
        fileStorage.setContentType(multipartFile.getContentType());
        fileStorage.setExtension(Objects.requireNonNull(multipartFile.getContentType()).split("/")[1]);
        fileStorage.setFileSize(multipartFile.getSize());
        fileStorage.setName(multipartFile.getOriginalFilename());
        fileStorage.setCreatedBy(SecurityUtils.getCurrentUserLogin().orElseThrow(RuntimeException::new));
        fileStorage.setLastModifiedBy(SecurityUtils.getCurrentUserLogin().orElseThrow(RuntimeException::new));
        return fileStorage;
    }

}
