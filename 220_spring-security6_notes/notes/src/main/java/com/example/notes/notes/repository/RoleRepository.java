package com.example.notes.notes.repository;

import com.example.notes.notes.model.AppRole;
import com.example.notes.notes.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(AppRole appRole);
}
