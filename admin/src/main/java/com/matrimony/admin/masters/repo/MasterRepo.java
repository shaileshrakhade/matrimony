package com.matrimony.admin.masters.repo;

import com.matrimony.admin.masters.model.Master;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MasterRepo extends MongoRepository<Master, String> {

    Optional<Master> findByKey(String key);
}
