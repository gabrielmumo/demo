package dev.gabrielmumo.demo.repository;

import dev.gabrielmumo.demo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(Role.Roles name);
}
