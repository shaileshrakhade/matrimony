package com.matrimony.biodata.service;

import com.matrimony.biodata.model.BioData;
import com.matrimony.biodata.repo.BioDatRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
@Async("asyncTaskExecutor")
public class AsyncServices {

    @Autowired
    private BioDatRepo bioDatRepo;

    public void save(BioData bioData) {
        log.debug("Async save Started!");
        bioDatRepo.save(bioData);
        log.debug("Async save End!");
    }

    public void deleteByRegisterAtBefore(Date date) {
        log.debug("Async deleteByRegisterAtBefore Started!");
        bioDatRepo.deleteByRegisterAtBefore(date);
        log.debug("Async deleteByRegisterAtBefore End!");
    }
}
