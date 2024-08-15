package com.sr.authentication.repo;

import com.sr.authentication.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByUserName(String username);

    Optional<Users> findByPhoneNumber(String phoneNumber);

    long deleteByIdAndUserName(Long id, String username);

    Optional<Users> findByUserNameOrPhoneNumber(String username, String phoneNumber);

    Optional<Users> findByIdAndUserName(Long userId, String username);
}
