package com.matrimony.admin.service;

import com.matrimony.admin.customExceptions.exceptions.AdminAttributesNotFoundException;
import com.matrimony.admin.dao.AdminDao;
import com.matrimony.admin.model.Admin;
import com.matrimony.admin.repo.AdminRepo;
import com.matrimony.admin.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Slf4j
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepo adminRepo;

    @Override
    public AdminDao add(AdminDao adminDao) {
        Admin admin = Admin.builder()
                .key(adminDao.getKey())
                .value(adminDao.getValue())
                .build();
        admin = adminRepo.save(admin);
        return AdminDao.builder()
                .id(admin.getId())
                .key(admin.getKey())
                .value(admin.getValue())
                .build();
    }

    @Override
    public AdminDao show(String key) throws AdminAttributesNotFoundException {
        Admin admin = adminRepo.findByKey(key).orElseThrow(() -> new AdminAttributesNotFoundException("key is found ::" + key));
        return AdminDao.builder()
                .id(admin.getId())
                .key(admin.getKey())
                .value(admin.getValue())
                .build();
    }

    @Override
    public List<AdminDao> showAll() {
        List<Admin> admin = adminRepo.findAll();
        return admin.stream().map(AdminDao::new).toList();

    }

    @Override
    public AdminDao update(AdminDao adminDao) throws AdminAttributesNotFoundException {
        Admin admin = adminRepo.findByKey(adminDao.getKey())
                .orElseThrow(() -> new AdminAttributesNotFoundException("key is found ::" + adminDao.getKey()));
        admin.setValue(adminDao.getValue());
        admin = adminRepo.save(admin);
        return AdminDao.builder()
                .id(admin.getId())
                .key(admin.getKey())
                .value(admin.getValue())
                .build();
    }

    @Override
    public AdminDao bioDataPdfUrl() throws AdminAttributesNotFoundException {
        Admin admin = adminRepo.findByKey(Constants.BIO_DATA_PDF_FILE_URL).orElseThrow(() -> new AdminAttributesNotFoundException("key is found ::" + Constants.BIO_DATA_PDF_FILE_URL));
        return AdminDao.builder()
                .id(admin.getId())
                .key(admin.getKey())
                .value(admin.getValue())
                .build();
    }

    @Override
    public AdminDao dashBoardMessage() throws AdminAttributesNotFoundException {
        Admin admin = adminRepo.findByKey(Constants.DASHBOARD_MESSAGE).orElseThrow(() -> new AdminAttributesNotFoundException("key is found ::" + Constants.DASHBOARD_MESSAGE));
        return AdminDao.builder()
                .id(admin.getId())
                .key(admin.getKey())
                .value(admin.getValue())
                .build();
    }

    @Override
    public AdminDao notificationMessage() throws AdminAttributesNotFoundException {
        Admin admin = adminRepo.findByKey(Constants.DEFAULT_NOTIFICATION).orElseThrow(() -> new AdminAttributesNotFoundException("key is found ::" + Constants.DEFAULT_NOTIFICATION));
        return AdminDao.builder()
                .id(admin.getId())
                .key(admin.getKey())
                .value(admin.getValue())
                .build();
    }

    @Override
    public boolean isPublish() throws AdminAttributesNotFoundException {
        Admin admin = adminRepo.findByKey(Constants.IS_BIO_DATA_PUBLISH).orElseThrow(() -> new AdminAttributesNotFoundException("key is found ::" + Constants.IS_BIO_DATA_PUBLISH));
        return Boolean.parseBoolean(admin.getValue());
    }

    @Override
    public AdminDao makePublish(boolean isPublish) throws AdminAttributesNotFoundException {
        Admin admin = adminRepo.findByKey(Constants.IS_BIO_DATA_PUBLISH)
                .orElseThrow(() -> new AdminAttributesNotFoundException("key is found ::" + Constants.IS_BIO_DATA_PUBLISH));
        admin.setValue(String.valueOf(isPublish));
        admin = adminRepo.save(admin);
        return AdminDao.builder()
                .id(admin.getId())
                .key(admin.getKey())
                .value(admin.getValue())
                .build();
    }
}
