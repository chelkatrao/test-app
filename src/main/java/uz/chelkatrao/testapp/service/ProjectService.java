package uz.chelkatrao.testapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.chelkatrao.testapp.domain.Project;
import uz.chelkatrao.testapp.repository.ProjectRepo;
import uz.chelkatrao.testapp.security.SecurityUtils;
import uz.chelkatrao.testapp.service.dto.ProjectDTO;
import uz.chelkatrao.testapp.service.dto.quiz.QuizDTO;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private final ProjectRepo projectRepo;

    @Autowired
    public ProjectService(ProjectRepo projectRepo) {
        this.projectRepo = projectRepo;
    }

    public Project createProject(ProjectDTO projectDTO) {
        Project project = new Project();
        project.setName(projectDTO.getName());
        project.setDescription(projectDTO.getDescription());
        project.setCreatedBy(SecurityUtils.getCurrentUserLogin().orElseThrow(RuntimeException::new));
        project.setLastModifiedBy(SecurityUtils.getCurrentUserLogin().orElseThrow(RuntimeException::new));
        return projectRepo.save(project);
    }

    public Project getProject(Long id) {
        return projectRepo.findById(id).orElse(null);
    }

    public Project updateProject(long id, ProjectDTO projectDTO) {
        Project project = projectRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        project.setName(projectDTO.getName());
        project.setDescription(projectDTO.getDescription());
        return projectRepo.save(project);
    }

    public void deleteProject(Long id) {
        projectRepo.deleteById(id);
    }

    public Page<ProjectDTO> projectList(Pageable pageable) {
        return projectRepo.findAll(pageable).map(project -> {
            ProjectDTO projectDTO = new ProjectDTO();
            projectDTO.setName(project.getName());
            projectDTO.setDescription(project.getDescription());
            projectDTO.setCreatedDate(project.getCreatedDate());
            Set<QuizDTO> quizDTOS = project.getQuizzes().stream().map(quiz -> {
                QuizDTO quizDTO = new QuizDTO();
                quizDTO.setId(quiz.getId());
                quizDTO.setName(quiz.getName());
                return quizDTO;
            }).collect(Collectors.toSet());
            projectDTO.setQuizDTOs(quizDTOS);
            return projectDTO;
        });
    }
}
