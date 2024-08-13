package com.matrimony.biodata.service;

import com.matrimony.biodata.customExceptions.exceptions.BioDataAlreadyApproveException;
import com.matrimony.biodata.customExceptions.exceptions.BioDataAlreadyExistException;
import com.matrimony.biodata.customExceptions.exceptions.BioDataNotFoundException;
import com.matrimony.biodata.dao.BioDataDao;
import com.matrimony.biodata.model.BioData;
import com.matrimony.biodata.repo.BioDatRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BioDataService implements BioDataOperations {

    @Autowired
    private BioDatRepo bioDatRepo;


    @Override
    public boolean approve(boolean isApprove, String id) throws BioDataNotFoundException {
        BioData bioData = bioDatRepo.findById(id).orElseThrow(() -> new BioDataNotFoundException("Bio-data is not exist"));
        bioData.setApprove(isApprove);
        bioDatRepo.save(bioData);
        return true;
    }

    @Override
    public List<BioDataDao> show() {
        return bioDatRepo.findAll()
                .stream().map(bioData -> BioDataDao.builder()
                        .id(bioData.getId())
                        .fullName(bioData.getFullName())
                        .isApprove(bioData.isApprove())
                        .build()).toList();

    }

    @Override
    public BioDataDao show(String id) throws BioDataNotFoundException {
        BioData bioData = bioDatRepo.findById(id).orElseThrow(() -> new BioDataNotFoundException("Bio-data is not exist"));

        return BioDataDao.builder()
                .id(bioData.getId())
                .fullName(bioData.getFullName())
                .isApprove(bioData.isApprove())
                .build();
    }

    @Override
    public BioDataDao showByUsername(String username) throws BioDataNotFoundException {
        BioData bioData = bioDatRepo.findByUsername(username).orElseThrow(() -> new BioDataNotFoundException("Bio-data is not exist"));

        return BioDataDao.builder()
                .id(bioData.getId())
                .fullName(bioData.getFullName())
                .isApprove(bioData.isApprove())
                .build();
    }

    @Override
    public BioDataDao add(BioDataDao bioDataDao, String username) throws BioDataAlreadyExistException {

        if (bioDatRepo.findByUsername(username).isPresent()) {
            throw new BioDataAlreadyExistException("Bio-data is already with this username exist");
        } else {
            BioData bioData = BioData.builder()
                    .username(username)
                    .fullName(bioDataDao.getFullName())
                    .isApprove(false)
                    .build();

            bioData = bioDatRepo.save(bioData);
            return BioDataDao.builder()
                    .id(bioData.getId())
                    .fullName(bioData.getFullName())
                    .isApprove(bioData.isApprove())
                    .build();


        }
    }

    @Override
    public BioDataDao update(BioDataDao bioDataDao, String username, String id) throws BioDataNotFoundException, BioDataAlreadyApproveException {
        BioData bioData = bioDatRepo.findByIdAndUsername(id, username).orElseThrow(() -> new BioDataNotFoundException("Bio-data is not exist"));
        if (!bioData.isApprove()) {
            bioData.setFullName(bioDataDao.getFullName());

            bioData = bioDatRepo.save(bioData);
            return BioDataDao.builder()
                    .id(bioData.getId())
                    .fullName(bioData.getFullName())
                    .isApprove(bioData.isApprove())
                    .build();
        }
        throw new BioDataAlreadyApproveException("Bio data is already approved");
    }

    @Override
    public boolean delete(String id) {
        bioDatRepo.deleteById(id);
        return true;
    }
}
