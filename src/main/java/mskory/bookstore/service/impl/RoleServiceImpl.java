package mskory.bookstore.service.impl;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mskory.bookstore.dto.RoleDto;
import mskory.bookstore.mapper.RoleMapper;
import mskory.bookstore.model.Role;
import mskory.bookstore.repository.RoleRepository;
import mskory.bookstore.service.RoleService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public RoleDto create(RoleDto requestDto) {
        Role mappedRole = roleMapper.toModel(requestDto);
        Role savedRole = roleRepository.save(mappedRole);
        return roleMapper.toDto(savedRole);
    }

    @Override
    public List<RoleDto> getAll(Pageable pageable) {
        return roleRepository.findAll(pageable)
                .stream()
                .map(roleMapper::toDto)
                .toList();
    }

    @Override
    public RoleDto getById(Long id) {
        return roleMapper.toDto(findRoleById(id));
    }

    @Override
    public RoleDto update(Long id, RoleDto requestDto) {
        Role updatedRole = roleMapper.update(findRoleById(id), requestDto);
        Role savedRole = roleRepository.save(updatedRole);
        return roleMapper.toDto(savedRole);
    }

    @Override
    public void deleteById(Long id) {
        roleRepository.deleteById(id);
    }

    private Role findRoleById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find role by id" + id));
    }
}
