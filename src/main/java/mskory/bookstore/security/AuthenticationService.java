package mskory.bookstore.security;

import mskory.bookstore.dto.user.UserLoginRequestDto;
import mskory.bookstore.dto.user.UserLoginResponseDto;
import mskory.bookstore.dto.user.UserRegisterRequestDto;
import mskory.bookstore.dto.user.UserResponseDto;
import mskory.bookstore.exception.RegistrationException;

public interface AuthenticationService {

    UserResponseDto register(UserRegisterRequestDto requestDto) throws RegistrationException;

    UserLoginResponseDto login(UserLoginRequestDto requestDto);
}
