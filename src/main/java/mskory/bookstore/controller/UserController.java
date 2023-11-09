package mskory.bookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mskory.bookstore.dto.user.UserRegisterRequestDto;
import mskory.bookstore.dto.user.UserResponseDto;
import mskory.bookstore.dto.user.UserUpdateRequestDto;
import mskory.bookstore.service.UserService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User management", description = "Allows to manage users table")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
    private final UserService userService;

    @Operation(summary = "Create new user", description = "Create new user record in DB")
    @PostMapping
    UserResponseDto create(@RequestBody UserRegisterRequestDto requestDto) {
        return userService.create(requestDto);
    }

    @Operation(summary = "Get multiple users",
            description = "Get multiple users, "
                    + "allow pagination and sorting, "
                    + "20 books starts from id 1 by default ")
    @GetMapping
    List<UserResponseDto> getAll(@ParameterObject Pageable pageable) {
        return userService.getAll(pageable);
    }

    @Operation(summary = "Get user by id",
            description = "Return user record from DB by provided id")
    @GetMapping("/{id}")
    UserResponseDto getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @Operation(summary = "Update user by id",
            description = "Update user with new provided fields by provided id")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER')")
    UserResponseDto updateById(Authentication authentication,
                               @PathVariable Long id,
                               @RequestBody UserUpdateRequestDto requestDto) {
        return userService.update(authentication, id, requestDto);
    }

    @Operation(summary = "Delete user by id",
            description = "Delete user record by provided id")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteById(@PathVariable Long id) {
        userService.deleteById(id);
    }
}
