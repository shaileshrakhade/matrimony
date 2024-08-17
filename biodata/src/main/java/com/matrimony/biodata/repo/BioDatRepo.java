package com.matrimony.biodata.repo;

import com.matrimony.biodata.model.BioData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface BioDatRepo extends MongoRepository<BioData, String> {
    Optional<BioData> findByIdAndUsername(String id, String username);

    Optional<BioData> findByUsername(String username);

    Optional<BioData> findByIdAndIsApprove(String id, boolean b);

    List<BioData> findAllByIsApprove(boolean b);

    long deleteByRegisterAtBefore(Date date);

    //    Optional<BioData> findByIdStartsWithOrFullNameContainsOrQualificationContainsOrAddressContains(Pageable pageable, String filter, String filter1, String filter2, String filter3);
//    @Query("{pages : {$fullName: { $regex : ?0 },qualification: { $regex : ?1 },address: { $regex : ?2 }}}")
    Page<BioData> findByIsApproveAndFullNameContainsIgnoreCaseOrQualificationContainsIgnoreCaseOrAddressContainsIgnoreCaseOrJobContainsIgnoreCase(Pageable pageable, boolean b, String filter, String filter1, String filter2, String filter3);
}
