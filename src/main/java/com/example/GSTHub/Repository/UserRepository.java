package com.example.GSTHub.Repository;

import com.example.GSTHub.Model.Role;
import com.example.GSTHub.Model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = "role")
    Optional<User> findByEmail(String email);

    @EntityGraph(attributePaths = "role")
    Page<User> findByActiveTrue(Pageable pageable);

    @EntityGraph(attributePaths = "role")
    Page<User> findByRole(Role role, Pageable pageable);

    @EntityGraph(attributePaths = "role")
    Page<User> findByRoleName(String roleName, Pageable pageable);

    boolean existsByEmail(String email);
}
