package com.matrimony.biodata.repo;

import com.matrimony.biodata.model.BioData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BioDatRepo extends MongoRepository<BioData,String > {
    Optional<BioData> findByIdAndUsername(String id, String username);

    Optional<BioData> findByUsername(String username);
}
