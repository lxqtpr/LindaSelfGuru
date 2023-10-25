package dev.lxqtpr.lindaSelfGuru.Domain.Users;

import dev.lxqtpr.lindaSelfGuru.Core.Services.MinioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final MinioService minioService;
    private final UserRepository userRepository;

    public void deleteUser(Long userId){
        minioService.deleteBucket(userId);
        userRepository.deleteById(userId);
    }
}
