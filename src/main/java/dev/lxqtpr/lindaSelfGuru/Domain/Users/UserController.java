package dev.lxqtpr.lindaSelfGuru.Domain.Users;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @DeleteMapping("/{id}")
    private void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }
}
