package dev.lxqtpr.lindaSelfGuru.Core.Security;

import dev.lxqtpr.lindaSelfGuru.Authentication.CustomUserDetails;
import dev.lxqtpr.lindaSelfGuru.Domain.Users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("securityExpression")
@RequiredArgsConstructor
public class CustomSecurityExpression {
    private final UserRepository userRepository;


    public boolean canAccessUserToLibrary(Long libraryId){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var principal = (CustomUserDetails) authentication.getPrincipal();
        return userRepository.isLibraryOwner(principal.getId(), libraryId);
    }
    public boolean canAccessUserToProject(Long projectId){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var principal = (CustomUserDetails) authentication.getPrincipal();
        return userRepository.isProjectOwner(principal.getId(), projectId);
    }
    public boolean canAccessUserToCategory(Long categoryId){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var principal = (CustomUserDetails) authentication.getPrincipal();
        return userRepository.isCategoryOwner(principal.getId(), categoryId);
    }
    public boolean canAccessUserToNote(Long noteId){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var principal = (CustomUserDetails) authentication.getPrincipal();
        return userRepository.isNoteOwner(principal.getId(), noteId);
    }
}
