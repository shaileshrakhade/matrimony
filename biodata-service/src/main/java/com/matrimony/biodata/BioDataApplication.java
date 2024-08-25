package com.matrimony.biodata;

import com.matrimony.biodata.masters.dao.MasterDao;
import com.matrimony.biodata.masters.service.MasterService;
import com.matrimony.biodata.masters.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;


@SpringBootApplication
@EnableDiscoveryClient
@CrossOrigin(origins = "*")
public class BioDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(BioDataApplication.class, args);
    }

    //consonant attributes will add
    @Autowired
    private MasterService masterService;

    @Bean
    public String defaultAttributesAdding() {
        try {
            masterService.add(MasterDao.builder().key(Constants.IS_BIO_DATA_PUBLISH).value(String.valueOf(true)).build());
            masterService.add(MasterDao.builder().key(Constants.BIO_DATA_NOT_PUBLISH_MSG).value("Bio-data is not publish yet.").build());
            masterService.add(MasterDao.builder().key(Constants.IS_BIO_DATA_REGISTRATION_CLOSE).value(String.valueOf(false)).build());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "default IS_BIO_DATA_PUBLISH";
    }
}
