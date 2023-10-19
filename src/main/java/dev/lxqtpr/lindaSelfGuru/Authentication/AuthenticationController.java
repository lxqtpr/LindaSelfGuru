package dev.lxqtpr.lindaSelfGuru.Authentication;

import dev.lxqtpr.lindaSelfGuru.Domain.User.Dto.CreateUserDto;
import dev.lxqtpr.lindaSelfGuru.Domain.User.Dto.LoginUserDto;
import dev.lxqtpr.lindaSelfGuru.Domain.User.Dto.ResponseUserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/registration")
    public ResponseEntity<ResponseUserDto> registration(@ModelAttribute @Valid CreateUserDto createUserDto){
        return ResponseEntity.ok(
                authenticationService.registration(createUserDto)
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseUserDto> login(@RequestBody @Valid LoginUserDto loginUserDto){
        return ResponseEntity.ok(
                authenticationService.login(loginUserDto)
        );
    }
    @PostMapping("/refresh")
    public ResponseEntity<ResponseUserDto> refreshTokens(HttpServletRequest request){
        return ResponseEntity.ok(
                authenticationService.refreshTokens(request)
        );
    }
}
