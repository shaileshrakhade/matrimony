package com.matrimony.biodata.masters.service;

import com.matrimony.biodata.masters.dao.MasterDao;
import com.matrimony.biodata.masters.exceptions.MasterAttributesNotFoundException;
import com.matrimony.biodata.masters.model.Master;
import com.matrimony.biodata.masters.repo.MasterRepo;
import com.matrimony.biodata.masters.util.Constants;
import com.matrimony.biodata.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MasterServiceImpl implements MasterService {
    @Value("${biodata.other.doc.path}")
    private String otherDoc;

    private final MasterRepo masterRepo;
    private final FileService fileService;

    @Override
    public MasterDao add(MasterDao masterDao) {
        Optional<Master> masterOptional = masterRepo.findByKey(masterDao.getKey());
        masterOptional.ifPresent(value -> masterDao.setId(value.getId()));
        Master master = Master.builder()
                .id(masterDao.getId())
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
    public boolean isRegistrationClose() throws MasterAttributesNotFoundException {
        Master master = masterRepo.findByKey(Constants.IS_BIO_DATA_REGISTRATION_CLOSE).orElseThrow(() -> new MasterAttributesNotFoundException("key is found ::" + Constants.IS_BIO_DATA_PUBLISH));
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

    @Override
    public void publishBiodataPDF(MultipartFile file) throws IOException, MasterAttributesNotFoundException {
        Master master = masterRepo.findByKey(Constants.BIO_DATA_PDF_PUBLISH_URL)
                .orElseGet(() -> {
                    return Master.builder()
                            .key(Constants.BIO_DATA_PDF_PUBLISH_URL)
                            .value(file.getOriginalFilename())
                            .build();
                });

        fileService.uploadFile(otherDoc, file.getOriginalFilename(), file);
        master.setValue(file.getOriginalFilename());
        master = masterRepo.save(master);

    }


}
