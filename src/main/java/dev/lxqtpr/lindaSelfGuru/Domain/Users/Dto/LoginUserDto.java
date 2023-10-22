package dev.lxqtpr.lindaSelfGuru.Domain.Users.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginUserDto {
    @NotEmpty
    String password;
    @NotEmpty
    @Email
    String email;
}
