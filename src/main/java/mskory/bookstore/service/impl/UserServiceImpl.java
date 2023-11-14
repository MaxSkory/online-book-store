package mskory.bookstore.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import mskory.bookstore.dto.user.UserRegisterRequestDto;
import mskory.bookstore.dto.user.UserResponseDto;
import mskory.bookstore.dto.user.UserUpdateRequestDto;
import mskory.bookstore.mapper.UserMapper;
import mskory.bookstore.model.Role;
import mskory.bookstore.model.RoleName;
import mskory.bookstore.model.User;
import mskory.bookstore.repository.RoleRepository;
import mskory.bookstore.repository.UserRepository;
import mskory.bookstore.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final RoleName DEFAULT_ROLE_NAME = RoleName.USER;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto create(UserRegisterRequestDto requestDto) {
        User mappedUser = userMapper.toModel(requestDto);
        mappedUser.setRoles(Set.of(getDefaultRole()));
        mappedUser.setPassword(passwordEncoder.encode(mappedUser.getPassword()));
        User savedUser = userRepository.save(mappedUser);
        return userMapper.toDto(savedUser);
    }

    @Override
    public List<UserResponseDto> getAll(Pageable pageable) {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    public UserResponseDto getById(Long id) {
        return userMapper.toDto(findUserById(id));
    }

    @Override
    public UserResponseDto update(
            Authentication authentication,
            Long id, UserUpdateRequestDto requestDto) {
        User userById = findUserById(id);
        if (!isAdmin(authentication)) {
            userCheck(authentication, userById);
        }
        if (requestDto.password() != null) {
            checkOldPassword(requestDto, userById);
        }
        User updatedUser = userMapper.update(userById, requestDto);
        User savedUser = userRepository.save(updatedUser);
        return userMapper.toDto(savedUser);
    }

    private void userCheck(Authentication authentication, User userById) {
        if (!authentication.getName().equals(userById.getEmail())) {
            throw new AccessDeniedException("Update not allowed! "
                    + "Authenticated user don't have permissions to update account with id "
                    + userById.getId());
        }
    }

    private boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"));
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    private void checkOldPassword(UserUpdateRequestDto requestDto, User userFromDb) {
        if (passwordEncoder.matches(requestDto.oldPassword(), userFromDb.getPassword())) {
            throw new ValidationException("Old password is wrong");
        }
    }

    private User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Can't find user by id " + id));
    }

    private Role getDefaultRole() {
        return roleRepository.findByName(DEFAULT_ROLE_NAME).orElseThrow(() ->
                new EntityNotFoundException("Can't find default role " + DEFAULT_ROLE_NAME));
    }
}
