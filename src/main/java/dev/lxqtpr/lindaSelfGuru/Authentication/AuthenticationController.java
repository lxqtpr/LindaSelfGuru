package dev.lxqtpr.lindaSelfGuru.Authentication;

import dev.lxqtpr.lindaSelfGuru.Domain.Users.Dto.CreateUserDto;
import dev.lxqtpr.lindaSelfGuru.Domain.Users.Dto.LoginUserDto;
import dev.lxqtpr.lindaSelfGuru.Domain.Users.Dto.ResponseUserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/registration")
    public ResponseUserDto registration(@ModelAttribute @Valid CreateUserDto createUserDto){
        return authenticationService.registration(createUserDto);
    }

    @PostMapping("/login")
    public ResponseUserDto login(@RequestBody @Valid LoginUserDto loginUserDto){
        return authenticationService.login(loginUserDto);
    }
    @PostMapping("/refresh")
    public ResponseUserDto refreshTokens(HttpServletRequest request){
        return authenticationService.refreshTokens(request);
    }
}
