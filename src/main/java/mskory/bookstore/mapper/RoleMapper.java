package mskory.bookstore.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import mskory.bookstore.config.MapperConfig;
import mskory.bookstore.dto.RoleDto;
import mskory.bookstore.exception.RoleNotFoundException;
import mskory.bookstore.model.Role;
import mskory.bookstore.model.RoleName;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface RoleMapper {

    @Mapping(target = "id", ignore = true)
    Role toModel(RoleDto dto);

    RoleDto toDto(Role role);

    Role update(@MappingTarget Role role, RoleDto dto);

    default RoleName getRoleByName(String roleName) {
        try {
            return RoleName.valueOf(roleName.toUpperCase());
        } catch (Exception e) {
            throw new RoleNotFoundException("Role \"" + roleName + "\" does not exist", e);
        }
    }

    @Named("rolesSetToStringSet")
    default Set<String> toRoleName(Set<Role> roles) {
        return roles == null ? null : roles.stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toSet());
    }
}
