package mskory.bookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mskory.bookstore.dto.RoleDto;
import mskory.bookstore.service.RoleService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Role management", description = "Allows to manage roles table")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/roles")
@PreAuthorize("hasAuthority('ADMIN')")
public class RoleController {
    private final RoleService roleService;

    @Operation(summary = "Create new role", description = "Create new role record in DB")
    @PostMapping
    RoleDto create(@RequestBody RoleDto requestDto) {
        return roleService.create(requestDto);
    }

    @Operation(summary = "Get multiple roles",
            description = "Get multiple roles, "
                    + "allow pagination and sorting, "
                    + "20 books starts from id 1 by default ")
    @GetMapping
    List<RoleDto> getAll(@ParameterObject Pageable pageable) {
        return roleService.getAll(pageable);
    }

    @Operation(summary = "Get role by id",
            description = "Return role record from DB by provided id")
    @GetMapping("/{id}")
    RoleDto getById(@PathVariable Long id) {
        return roleService.getById(id);
    }

    @Operation(summary = "Update role by id",
            description = "Update role with new provided fields by provided id")
    @PutMapping("/{id}")
    RoleDto updateById(@PathVariable Long id, @RequestBody RoleDto requestDto) {
        return roleService.update(id, requestDto);
    }

    @Operation(summary = "Delete role by id",
            description = "Delete role record by provided id")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteById(@PathVariable Long id) {
        roleService.deleteById(id);
    }
}
