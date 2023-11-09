package mskory.bookstore.service;

import java.util.List;
import mskory.bookstore.dto.RoleDto;
import org.springframework.data.domain.Pageable;

public interface RoleService {
    RoleDto create(RoleDto requestDto);

    List<RoleDto> getAll(Pageable pageable);

    RoleDto getById(Long id);

    RoleDto update(Long id, RoleDto requestDto);

    void deleteById(Long id);
}
