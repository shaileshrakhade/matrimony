package com.matrimony.admin;

import com.matrimony.admin.model.Admin;
import com.matrimony.admin.repo.AdminRepo;
import com.matrimony.admin.util.Constants;
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
    private AdminRepo adminRepo;

    @Bean
    public String defaultAttributesAdding() {
		List<Admin> adminList=new ArrayList<>();
		adminList.add(0, Admin.builder().key(Constants.IS_BIO_DATA_PUBLISH).value(String.valueOf(false)).build());
        adminList.add(0, Admin.builder().key(Constants.DEFAULT_NOTIFICATION).value("Application Starting soon...").build());
        adminList.add(0, Admin.builder().key(Constants.DASHBOARD_MESSAGE).value("Welcome to Matrimony").build());
        adminList.add(0, Admin.builder().key(Constants.BIO_DATA_PDF_FILE_URL).value("").build());
        try {
            adminRepo.saveAll(adminList);
            return "BIO_DATA_PDF_FILE_URL attributes was added";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "";
    }
}
