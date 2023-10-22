package dev.lxqtpr.lindaSelfGuru.Domain.Users.Dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseUserDto{
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String accessToken;
    private String refreshToken;
    private String avatar;
    private String role;
}
