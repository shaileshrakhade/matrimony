package com.lagn.authentication.repo;

import com.lagn.authentication.model.OAuth2Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface OAuth2UserRepository extends JpaRepository<OAuth2Users,Long> {
    Optional<OAuth2Users> findByEmail(String email);
}
