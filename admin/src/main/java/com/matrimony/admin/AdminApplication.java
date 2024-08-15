package com.matrimony.admin;

import com.matrimony.admin.masters.dao.MasterDao;
import com.matrimony.admin.masters.model.Master;
import com.matrimony.admin.masters.repo.MasterRepo;
import com.matrimony.admin.masters.service.MasterService;
import com.matrimony.admin.masters.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

    //consonant attributes will add
    @Autowired
    private MasterService masterService;

    @Bean
    public String defaultAttributesAdding() {
        try {
            masterService.add(MasterDao.builder().key(Constants.DEFAULT_NOTIFICATION).value("Application Starting soon...").build());
            masterService.add(MasterDao.builder().key(Constants.DASHBOARD_MESSAGE).value("Welcome to Matrimony").build());
            masterService.add(MasterDao.builder().key(Constants.BIO_DATA_PDF_FILE_URL).value("/bio-data.pdf").build());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "";
    }
}
