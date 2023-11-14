package mskory.bookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mskory.bookstore.dto.user.UserLoginRequestDto;
import mskory.bookstore.dto.user.UserLoginResponseDto;
import mskory.bookstore.dto.user.UserRegisterRequestDto;
import mskory.bookstore.dto.user.UserResponseDto;
import mskory.bookstore.exception.RegistrationException;
import mskory.bookstore.security.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication endpoints", description = "Registration and login in")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Operation(summary = "Register new user",
            description = "Register new user with default authorities")
    @PostMapping("/register")
    public UserResponseDto register(
            @RequestBody @Valid UserRegisterRequestDto requestDto) throws RegistrationException {
        return authenticationService.register(requestDto);
    }

    @Operation(summary = "Login in endpoint", description = "Return JWT token on success")
    @PostMapping("/login")
    public UserLoginResponseDto login(@RequestBody UserLoginRequestDto requestDto) {
        return authenticationService.login(requestDto);
    }
}
