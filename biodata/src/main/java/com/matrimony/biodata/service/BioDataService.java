package com.matrimony.biodata.service;

import com.matrimony.biodata.customExceptions.exceptions.BioDataAlreadyApproveException;
import com.matrimony.biodata.customExceptions.exceptions.BioDataNotFoundException;
import com.matrimony.biodata.dao.BioDataDao;
import com.matrimony.biodata.model.BioData;
import com.matrimony.biodata.repo.BioDatRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BioDataService implements BioDataOperations {

    @Autowired
    private BioDatRepo bioDatRepo;


    @Override
    public boolean approve(String id, boolean isApprove) throws BioDataNotFoundException {
        BioData bioData = bioDatRepo.findById(id).orElseThrow(() -> new BioDataNotFoundException("Bio-data is not exist"));
        bioData.setApprove(isApprove);
        bioDatRepo.save(bioData);
        return true;
    }

    @Override
    public BioDataDao show(String id, String username) throws BioDataNotFoundException {
        BioData bioData = bioDatRepo.findByIdAndUsername(id, username).orElseThrow(() -> new BioDataNotFoundException("Bio-data is not exist"));

        return BioDataDao.builder()
                .id(bioData.getId())
                .username(bioData.getUsername())
                .fullName(bioData.getFullName())
                .isApprove(bioData.isApprove())
                .build();
    }

    @Override
    public BioDataDao show(String id) throws BioDataNotFoundException {
        BioData bioData = bioDatRepo.findById(id).orElseThrow(() -> new BioDataNotFoundException("Bio-data is not exist"));

        return BioDataDao.builder()
                .id(bioData.getId())
                .username(bioData.getUsername())
                .fullName(bioData.getFullName())
                .isApprove(bioData.isApprove())
                .build();
    }

    @Override
    public String add(BioDataDao bioDataDao) {
        BioData data = BioData.builder()
                .username(bioDataDao.getUsername())
                .fullName(bioDataDao.getFullName())
                .isApprove(false)
                .build();

        return bioDatRepo.save(data).getId();
    }

    @Override
    public String update(BioDataDao bioDataDao, String id) throws BioDataNotFoundException, BioDataAlreadyApproveException {
        BioData bioData = bioDatRepo.findById(id).orElseThrow(() -> new BioDataNotFoundException("Bio-data is not exist"));
        if (!bioData.isApprove()) {
            bioData.setFullName(bioDataDao.getFullName());
            return bioDatRepo.save(bioData).getId();
        }
        throw new BioDataAlreadyApproveException("Bio data is already approved");
    }
}
