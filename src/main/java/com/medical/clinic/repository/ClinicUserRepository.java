package com.medical.clinic.repository;

import com.medical.clinic.entity.ClinicUser;
import com.medical.clinic.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClinicUserRepository extends JpaRepository<ClinicUser, Long> {

    @Query("SELECT u FROM ClinicUser u WHERE u.role = :role")
    List<ClinicUser> findAllByRole(@Param("role") Role role);

    Optional<ClinicUser> findByUsername(String username);

    Optional<ClinicUser> findByEmail(String email);

    Optional<ClinicUser> findById(Long id);


    boolean existsByUsername(String username);
}
