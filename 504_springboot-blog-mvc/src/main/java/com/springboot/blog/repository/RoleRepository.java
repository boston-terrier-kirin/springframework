package com.springboot.blog.repository;

import com.springboot.blog.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    public Role findByName(String name);
}
