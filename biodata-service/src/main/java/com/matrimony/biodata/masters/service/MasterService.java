package com.matrimony.biodata.masters.service;

import com.matrimony.biodata.masters.dao.MasterDao;
import com.matrimony.biodata.masters.exceptions.MasterAttributesAlreadyExitException;
import com.matrimony.biodata.masters.exceptions.MasterAttributesNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MasterService {
    MasterDao add(MasterDao masterDao) throws MasterAttributesAlreadyExitException;

    MasterDao show(String key) throws MasterAttributesNotFoundException;

    List<MasterDao> showAll();

    MasterDao update(MasterDao masterDao) throws MasterAttributesNotFoundException;

    boolean isPublish() throws MasterAttributesNotFoundException;
    public boolean isRegistrationClose() throws MasterAttributesNotFoundException;
    boolean delete(String key) throws MasterAttributesNotFoundException;

    void publishBiodataPDF(MultipartFile file) throws IOException, MasterAttributesNotFoundException;
}
