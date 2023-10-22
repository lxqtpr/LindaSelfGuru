package dev.lxqtpr.lindaSelfGuru.Domain.Users.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreateUserDto{
    @NotEmpty
    String firstName;
    @NotEmpty
    String lastName;
    @NotEmpty
    String password;

    @Email
    @NotEmpty
    String email;

    MultipartFile avatar;
}
