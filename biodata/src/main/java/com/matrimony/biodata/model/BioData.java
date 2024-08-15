package com.matrimony.biodata.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(value = "biodata")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BioData {
    @Id
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
    private String picUrl;
    private String paymentUrl;
    private String comment;
    private Date registerAt=new Date();
    private boolean isUpdate;
    private boolean isApprove;
    private String operationsBy;
}
