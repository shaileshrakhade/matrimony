package com.matrimony.biodata.dao;

import com.matrimony.biodata.model.BioData;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BioDataDao {
    private String id;
    private String username;
    private String fullName;
    private String phoneNumber;
    private String alternateNumber;
    private String birthDate;
    private String birthPlace;
    private String height;
    private String gotra;
    private String gan;
    private String nstrakhan;
    private String nadi;
    private String bloodGroup;
    private String qualification;
    private String job;
    private String familyInformation;
    private String address;
    private String mamkul;
    private String paymentUrl;
    private String picUrl;
    private String comment;
    private Date registerAt;
    private boolean isLock;
    private boolean isApprove;

}
