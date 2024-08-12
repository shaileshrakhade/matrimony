package com.matrimony.biodata.repo;

import com.matrimony.biodata.model.BioData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BioDatRepo extends MongoRepository<BioData,String > {
}
