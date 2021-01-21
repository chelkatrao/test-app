package uz.chelkatrao.testapp.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.chelkatrao.testapp.domain.Project;
import uz.chelkatrao.testapp.service.ProjectService;
import uz.chelkatrao.testapp.service.dto.ProjectDTO;

@RestController
@RequestMapping("/api/project")
public class ProjectResource {

    private final ProjectService projectService;

    @Autowired
    public ProjectResource(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/new")
    public ResponseEntity<Project> createProject(@RequestBody ProjectDTO projectDTO) {
        return ResponseEntity.ok(projectService.createProject(projectDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProject(@PathVariable Long id) {
        Project project = projectService.getProject(id);
        if (project == null) {
            return new ResponseEntity<>(new Project(), HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(projectService.getProject(id));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable long id, @RequestBody ProjectDTO projectDTO) {
        return ResponseEntity.ok(projectService.updateProject(id, projectDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
    }

    @GetMapping("/list")
    public ResponseEntity<Page<ProjectDTO>> projectList(Pageable pageable) {
        return ResponseEntity.ok(projectService.projectList(pageable));
    }

}
