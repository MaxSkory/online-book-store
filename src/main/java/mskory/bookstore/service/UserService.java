package mskory.bookstore.service;

import java.util.List;
import mskory.bookstore.dto.user.UserRegisterRequestDto;
import mskory.bookstore.dto.user.UserResponseDto;
import mskory.bookstore.dto.user.UserUpdateRequestDto;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface UserService {
    UserResponseDto create(UserRegisterRequestDto requestDto);

    List<UserResponseDto> getAll(Pageable pageable);

    UserResponseDto getById(Long id);

    UserResponseDto update(Authentication authentication, Long id, UserUpdateRequestDto requestDto);

    void deleteById(Long id);
}
