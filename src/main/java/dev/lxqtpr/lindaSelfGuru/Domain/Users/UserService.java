package dev.lxqtpr.lindaSelfGuru.Domain.Users;

import dev.lxqtpr.lindaSelfGuru.Core.Services.MinioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final MinioService minioService;
    private final UserRepository userRepository;

//    public String deleteUser(Long userId){
//        var user = userRepository.findById(userId)
//                .orElseThrow(() -> new ResourceNotFoundException("User with this id does not exist"));
//        user.getProjects().forEach(projectEntity -> {
//           fileService.deleteFile( projectEntity.getSong().getFileName());
//           fileService.deleteFile(projectEntity.getVoiceRecording());
//        });
//        user.getLibrary().forEach(libraryEntity -> {
//            fileService.deleteFile(libraryEntity.getAvatar());
//            libraryEntity
//                    .getCategories()
//                    .forEach(category -> {
//                        category.getSongs()
//                                .forEach(songEntity -> fileService.deleteFile(songEntity.getFileName()));
//                    });
//        });
//    }
}
