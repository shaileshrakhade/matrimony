package com.matrimony.biodata.masters.service;

import com.matrimony.biodata.masters.dao.MasterDao;
import com.matrimony.biodata.masters.exceptions.MasterAttributesAlreadyExitException;
import com.matrimony.biodata.masters.exceptions.MasterAttributesNotFoundException;
import com.matrimony.biodata.masters.model.Master;
import com.matrimony.biodata.masters.repo.MasterRepo;
import com.matrimony.biodata.masters.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MasterServiceImpl implements MasterService {

    private final MasterRepo masterRepo;

    @Override
    public MasterDao add(MasterDao masterDao) throws MasterAttributesAlreadyExitException {
        if (masterRepo.findByKey(masterDao.getKey()).isPresent())
            throw new MasterAttributesAlreadyExitException("Key is already present");
        Master master = Master.builder()
                .key(masterDao.getKey())
                .value(masterDao.getValue())
                .build();
        master = masterRepo.save(master);
        return MasterDao.builder()
                .id(master.getId())
                .key(master.getKey())
                .value(master.getValue())
                .build();
    }

    @Override
    public MasterDao show(String key) throws MasterAttributesNotFoundException {
        Master master = masterRepo.findByKey(key)
                .orElseThrow(() -> new MasterAttributesNotFoundException("Not Exist :: " + key));
        return MasterDao.builder()
                .id(master.getId())
                .key(master.getKey())
                .value(master.getValue())
                .build();
    }

    @Override
    public List<MasterDao> showAll() {
        List<Master> master = masterRepo.findAll();
        return master.stream().map(MasterDao::new).toList();

    }

    @Override
    public MasterDao update(MasterDao masterDao) throws MasterAttributesNotFoundException {
        Master master = masterRepo.findByKey(masterDao.getKey())
                .orElseThrow(() -> new MasterAttributesNotFoundException("Not Exit :: " + masterDao.getKey()));
        master.setValue(masterDao.getValue());
        master = masterRepo.save(master);
        return MasterDao.builder()
                .id(master.getId())
                .key(master.getKey())
                .value(master.getValue())
                .build();
    }

    @Override
    public boolean isPublish() throws MasterAttributesNotFoundException {
        Master master = masterRepo.findByKey(Constants.IS_BIO_DATA_PUBLISH).orElseThrow(() -> new MasterAttributesNotFoundException("key is found ::" + Constants.IS_BIO_DATA_PUBLISH));
        return Boolean.parseBoolean(master.getValue());
    }

    @Override
    @Transactional
    public boolean delete(String key) throws MasterAttributesNotFoundException {
        masterRepo.findByKey(key)
                .orElseThrow(() -> new MasterAttributesNotFoundException("Not Exit :: " + key));
        masterRepo.deleteByKey(key);
        return true;
    }


}
