package com.matrimony.admin.masters.service;

import com.matrimony.admin.masters.dao.MasterDao;
import com.matrimony.admin.masters.exceptions.MasterAttributesAlreadyExitException;
import com.matrimony.admin.masters.exceptions.MasterAttributesNotFoundException;
import com.matrimony.admin.masters.model.Master;
import com.matrimony.admin.masters.repo.MasterRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminServiceImpl implements MasterService {

    private final MasterRepo masterRepo;

    @Override
    public MasterDao add(MasterDao masterDao) throws MasterAttributesAlreadyExitException {
        if(masterRepo.findByKey(masterDao.getKey()).isPresent())
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
        Master Master = masterRepo.findByKey(key).orElseThrow(() -> new MasterAttributesNotFoundException("key is found ::" + key));
        return MasterDao.builder()
                .id(Master.getId())
                .key(Master.getKey())
                .value(Master.getValue())
                .build();
    }

    @Override
    public List<MasterDao> showAll() {
        List<Master> master = masterRepo.findAll();
        return master.stream().map(MasterDao::new).toList();

    }

    @Override
    public MasterDao update(MasterDao MasterDao) throws MasterAttributesNotFoundException {
        Master master = masterRepo.findByKey(MasterDao.getKey())
                .orElseThrow(() -> new MasterAttributesNotFoundException("key is found ::" + MasterDao.getKey()));
        master.setValue(MasterDao.getValue());
        master = masterRepo.save(master);
        return MasterDao.builder()
                .id(master.getId())
                .key(master.getKey())
                .value(master.getValue())
                .build();
    }


}
