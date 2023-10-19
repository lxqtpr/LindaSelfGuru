package dev.lxqtpr.lindaSelfGuru.Authentication;

import dev.lxqtpr.lindaSelfGuru.Core.Services.FileService;
import dev.lxqtpr.lindaSelfGuru.Domain.User.Dto.CreateUserDto;
import dev.lxqtpr.lindaSelfGuru.Domain.User.Dto.LoginUserDto;
import dev.lxqtpr.lindaSelfGuru.Domain.User.Dto.ResponseUserDto;
import dev.lxqtpr.lindaSelfGuru.Domain.User.RoleEnum;
import dev.lxqtpr.lindaSelfGuru.Domain.User.UserEntity;
import dev.lxqtpr.lindaSelfGuru.Domain.User.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

import java.net.http.HttpRequest;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final FileService fileService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public ResponseUserDto registration(CreateUserDto createUserDto) {
        if (userRepository.existsByEmail(createUserDto.getEmail()))
            throw new IllegalArgumentException("User already exist");

        var userToSave = modelMapper.map(createUserDto, UserEntity.class);
        userToSave.setRole(RoleEnum.ROLE_ADMIN);
        userToSave.setAvatar(fileService.upload(createUserDto.getAvatar()));
        userToSave.setPassword(passwordEncoder.encode(createUserDto.getPassword()));

        var res = modelMapper.map(userRepository.save(userToSave), ResponseUserDto.class);
        res.setAccessToken(jwtService.generateAccessToken(userToSave));
        res.setRefreshToken(jwtService.generateRefreshToken(userToSave));

        return res;
    }

    public ResponseUserDto login(LoginUserDto loginUserDto){
        var user = userRepository.findByEmail(loginUserDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User does not exist"));
        if (!passwordEncoder.matches(loginUserDto.getPassword(), user.getPassword())){
            throw new IllegalArgumentException("Password does not match");
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUserDto.getEmail(),
                        loginUserDto.getPassword()
                )
        );
        var res = modelMapper.map(user, ResponseUserDto.class);
        res.setAccessToken(jwtService.generateAccessToken(user));
        res.setRefreshToken(jwtService.generateRefreshToken(user));
        return res;
    }

    public ResponseUserDto refreshTokens(HttpServletRequest request){
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("invalid refreshToken");
        }
        refreshToken = authHeader.substring(7);
        var userEmail = jwtService.getUserEmailFromRefreshClaims(refreshToken);
        if (userEmail.isEmpty()) throw new IllegalArgumentException("invalid refreshToken");
        var user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User does not exist"));
        var res = modelMapper.map(user, ResponseUserDto.class);
        res.setAccessToken(jwtService.generateAccessToken(user));
        res.setRefreshToken(jwtService.generateRefreshToken(user));
        return res;
    }
}
