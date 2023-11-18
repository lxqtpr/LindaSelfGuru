package dev.lxqtpr.lindaSelfGuru.Domain.Projects;

import dev.lxqtpr.lindaSelfGuru.Domain.Projects.Dto.CreateProjectDto;
import dev.lxqtpr.lindaSelfGuru.Domain.Projects.Dto.ResponseProjectDto;
import dev.lxqtpr.lindaSelfGuru.Domain.Projects.Dto.UpdateProjectDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping("/{userId}")
    public List<ResponseProjectDto> getAllUserProjects(@PathVariable Long userId){
        return projectService.getAllUserProjects(userId);
    }

    @PostMapping
    public ResponseProjectDto createProject(@RequestBody @Valid CreateProjectDto dto){
        return projectService.createProject(dto);
    }

    @PutMapping
    @PreAuthorize("@securityExpression.canAccessUserToProject(#dto.id)")
    public ResponseProjectDto updateProject(@RequestBody @Valid UpdateProjectDto dto){
        return projectService.updateProject(dto);
    }

    @DeleteMapping("/{projectId}/{userId}")
    @PreAuthorize("@securityExpression.canAccessUserToProject(#projectId)")
    public void deleteProject(@PathVariable Long projectId, @PathVariable Long userId){
       projectService.deleteProject(userId, projectId);
    }
}
