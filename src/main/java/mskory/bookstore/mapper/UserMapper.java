package mskory.bookstore.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import mskory.bookstore.config.MapperConfig;
import mskory.bookstore.dto.user.UserRegisterRequestDto;
import mskory.bookstore.dto.user.UserResponseDto;
import mskory.bookstore.dto.user.UserUpdateRequestDto;
import mskory.bookstore.model.Role;
import mskory.bookstore.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface UserMapper {

    User toModel(UserRegisterRequestDto requestDto);

    UserResponseDto toDto(User user);

    User update(@MappingTarget User user, UserUpdateRequestDto requestDto);

    default Set<String> toRoleName(Set<Role> roles) {
        return roles == null ? null : roles.stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toSet());
    }
}
