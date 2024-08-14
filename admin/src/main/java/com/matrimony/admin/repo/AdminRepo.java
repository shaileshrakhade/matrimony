package com.matrimony.admin.repo;

import com.matrimony.admin.model.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AdminRepo extends MongoRepository<Admin, String> {

    Optional<Admin> findByKey(String key);
}
