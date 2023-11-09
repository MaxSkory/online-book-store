package mskory.bookstore.repository;

import java.util.Optional;
import mskory.bookstore.model.Role;
import mskory.bookstore.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}
