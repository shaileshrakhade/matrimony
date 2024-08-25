package com.matrimony.biodata.service;

import com.matrimony.biodata.model.BioData;
import com.matrimony.biodata.repo.BioDatRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Date;

@Service
@Slf4j
@Async("asyncTaskExecutor")
public class AsyncServicesImpl implements AsyncService {

    @Autowired
    private BioDatRepo bioDatRepo;

    @Override
    public void save(BioData bioData) {
        log.debug("Async save Started!");
        bioDatRepo.save(bioData);
        log.debug("Async save End!");
    }

    @Override
    public void deleteByRegisterAtBefore(Date date) {
        log.debug("Async deleteByRegisterAtBefore Started!");
        bioDatRepo.deleteByRegisterAtBefore(date);
        log.debug("Async deleteByRegisterAtBefore End!");
    }

    @Override
    public void uploadFile(String basePath, String fileName, MultipartFile multipartFile) throws IOException {
        log.debug("Async uploadFile Started!");
        File dir = new File(basePath);
        if (!dir.exists()) {
            boolean bool = dir.mkdirs();
            if (bool)
                log.debug("new folder is created :: ${}", basePath);
        }
        Path path = Path.of(basePath + fileName);
        try {
            Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        log.debug("Async uploadFile End!");
    }

    public InputStream getResource(String basePath, String fileName) throws FileNotFoundException {
        File dir = new File(basePath + fileName);
        return new FileInputStream(dir);
    }
}
