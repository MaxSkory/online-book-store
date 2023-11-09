package mskory.bookstore.security.impl;

import lombok.RequiredArgsConstructor;
import mskory.bookstore.dto.user.UserLoginRequestDto;
import mskory.bookstore.dto.user.UserLoginResponseDto;
import mskory.bookstore.dto.user.UserRegisterRequestDto;
import mskory.bookstore.dto.user.UserResponseDto;
import mskory.bookstore.exception.RegistrationException;
import mskory.bookstore.repository.UserRepository;
import mskory.bookstore.security.AuthenticationService;
import mskory.bookstore.security.JwtUtil;
import mskory.bookstore.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public UserResponseDto register(UserRegisterRequestDto requestDto)
            throws RegistrationException {
        if (userRepository.findByEmail(requestDto.email()).isPresent()) {
            throw new RegistrationException("User with email "
                    + requestDto.email() + " already exists");
        }
        return userService.create(requestDto);
    }

    @Override
    public UserLoginResponseDto login(UserLoginRequestDto requestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDto.email(), requestDto.password()));
        return new UserLoginResponseDto(jwtUtil.createToken(authentication.getName()));
    }
}
