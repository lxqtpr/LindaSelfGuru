package dev.lxqtpr.lindaSelfGuru.Domain.Projects;

import dev.lxqtpr.lindaSelfGuru.Domain.Projects.Dto.CreateProjectDto;
import dev.lxqtpr.lindaSelfGuru.Domain.Projects.Dto.ResponseProjectDto;
import dev.lxqtpr.lindaSelfGuru.Domain.Projects.Dto.UpdateProjectDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<ResponseProjectDto>> getAllUserProjects(@PathVariable Long userId){
        return ResponseEntity.ok(projectService.getAllUserProjects(userId));
    }

    @PostMapping
    public ResponseEntity<ResponseProjectDto> createProject(@ModelAttribute @Valid CreateProjectDto dto){
        return new ResponseEntity<>(projectService.createProject(dto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ResponseProjectDto> updateProject(@RequestBody @Valid UpdateProjectDto dto){
        return ResponseEntity.ok(projectService.updateProject(dto));
    }

    @DeleteMapping("/{projectId}/{userId}")
    public void deleteProject(@PathVariable Long projectId, @PathVariable Long userId){
       projectService.deleteProject(userId, projectId);
    }
}
