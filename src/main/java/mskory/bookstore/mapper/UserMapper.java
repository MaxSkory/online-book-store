package mskory.bookstore.mapper;

import mskory.bookstore.config.MapperConfig;
import mskory.bookstore.dto.user.UserRegisterRequestDto;
import mskory.bookstore.dto.user.UserResponseDto;
import mskory.bookstore.dto.user.UserUpdateRequestDto;
import mskory.bookstore.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class, uses = RoleMapper.class)
public interface UserMapper {

    User toModel(UserRegisterRequestDto requestDto);

    @Mapping(target = "roles", qualifiedByName = "rolesSetToStringSet")
    UserResponseDto toDto(User user);

    User update(@MappingTarget User user, UserUpdateRequestDto requestDto);
}
