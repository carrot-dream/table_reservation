package com.jmjung.table_reservation.repository.role;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByIdx(Long idx);
    boolean existsByIdx(Long idx);
}
