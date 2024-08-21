package com.matrimony.biodata.masters.repo;

import com.matrimony.biodata.masters.model.Master;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MasterRepo extends MongoRepository<Master, String> {

    Optional<Master> findByKey(String key);

    void deleteByKey(String key);
}
