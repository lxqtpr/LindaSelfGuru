package dev.lxqtpr.lindaSelfGuru.Authentication;

import dev.lxqtpr.lindaSelfGuru.Core.Excreptions.JwtException;
import dev.lxqtpr.lindaSelfGuru.Core.Excreptions.PasswordDoesNotMatchException;
import dev.lxqtpr.lindaSelfGuru.Core.Excreptions.ResourceNotFoundException;
import dev.lxqtpr.lindaSelfGuru.Core.Services.MinioService;
import dev.lxqtpr.lindaSelfGuru.Domain.Users.Dto.CreateUserDto;
import dev.lxqtpr.lindaSelfGuru.Domain.Users.Dto.LoginUserDto;
import dev.lxqtpr.lindaSelfGuru.Domain.Users.Dto.ResponseUserDto;
import dev.lxqtpr.lindaSelfGuru.Domain.Users.RoleEnum;
import dev.lxqtpr.lindaSelfGuru.Domain.Users.UserEntity;
import dev.lxqtpr.lindaSelfGuru.Domain.Users.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final MinioService minioService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public ResponseUserDto registration(CreateUserDto createUserDto) {
        if (userRepository.existsByEmail(createUserDto.getEmail()))
            throw new ResourceNotFoundException("User already exist");

        var userToSave = modelMapper.map(createUserDto, UserEntity.class);

        userToSave.setRole(RoleEnum.ROLE_USER);
        userToSave.setPassword(passwordEncoder.encode(createUserDto.getPassword()));

        var savedUser = userRepository.save(userToSave);
        if (createUserDto.getAvatar() != null){
            var file = minioService.upload(savedUser.getId(), createUserDto.getAvatar());
            userToSave.setAvatar(file);
        }

        var res = modelMapper.map(userRepository.save(userToSave), ResponseUserDto.class);

        res.setAccessToken(jwtService.generateAccessToken(userToSave));
        res.setRefreshToken(jwtService.generateRefreshToken(userToSave));

        return res;
    }

    public ResponseUserDto login(LoginUserDto loginUserDto){
        var user = userRepository.findByEmail(loginUserDto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User does not exist"));
        if (!passwordEncoder.matches(loginUserDto.getPassword(), user.getPassword())){
            throw new PasswordDoesNotMatchException("Password does not match");
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
        var authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new JwtException("invalid refreshToken");
        }
        var refreshToken = authHeader.substring(7);
        if (!jwtService.validateRefreshToken(refreshToken)){
            throw new JwtException("Refresh token does not valid");
        }
        var userEmail = jwtService.getUserEmailFromRefreshClaims(refreshToken);
        if (userEmail.isEmpty()) throw new JwtException("invalid refreshToken");

        var user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User does not exist"));
        var res = modelMapper.map(user, ResponseUserDto.class);

        res.setAccessToken(jwtService.generateAccessToken(user));
        res.setRefreshToken(jwtService.generateRefreshToken(user));
        return res;
    }
}
