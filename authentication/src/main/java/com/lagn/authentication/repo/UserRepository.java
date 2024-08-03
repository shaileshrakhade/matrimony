package com.lagn.authentication.repo;

import com.lagn.authentication.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users,Long> {

    Optional<Users> findByMobile(String mobile);
}
