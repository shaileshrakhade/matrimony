package com.matrimony.biodata.service;

import com.matrimony.biodata.customExceptions.exceptions.BioDataAlreadyApproveException;
import com.matrimony.biodata.customExceptions.exceptions.BioDataAlreadyExistException;
import com.matrimony.biodata.customExceptions.exceptions.BioDataNotFoundException;
import com.matrimony.biodata.customExceptions.exceptions.BioDataUpdateException;
import com.matrimony.biodata.dao.BioDataDao;
import com.matrimony.biodata.model.BioData;
import com.matrimony.biodata.repo.BioDatRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BioDataServiceImpl implements BioDataService {

    @Autowired
    private BioDatRepo bioDatRepo;


    @Override
    public boolean approve(boolean isApprove, String id,String username) throws BioDataNotFoundException {
        BioData bioData = bioDatRepo.findById(id).orElseThrow(() -> new BioDataNotFoundException("Bio-data is not exist"));
        bioData.setComment("Bio Data Application approved");
        bioData.setApprove(isApprove);
        bioData.setOperationsBy(username);
        bioDatRepo.save(bioData);
        return true;
    }

    @Override
    public boolean shouldUpdate(boolean isUpdate, String id,String comments,String username) throws BioDataNotFoundException {
        BioData bioData = bioDatRepo.findById(id).orElseThrow(() -> new BioDataNotFoundException("Bio-data is not exist"));
        if (isUpdate)
            bioData.setApprove(false);
        bioData.setUpdate(isUpdate);
        bioData.setComment(comments);
        bioData.setOperationsBy(username);
        bioDatRepo.save(bioData);
        return true;
    }

    @Override
    public List<BioDataDao> show(boolean isApprove) {
        return bioDatRepo.findAllByIsApprove(isApprove)
                .stream().map(bioData -> BioDataDao.builder()
                        .id(bioData.getId())
                        .fullName(bioData.getFullName())
                        .phoneNumber(bioData.getPhoneNumber())
                        .alternateNumber(bioData.getAlternateNumber())
                        .birthDate(bioData.getBirthDate())
                        .birthPlace(bioData.getBirthPlace())
                        .height(bioData.getHeight())
                        .gotra(bioData.getGotra())
                        .gan(bioData.getGan())
                        .nstrakhan(bioData.getNstrakhan())
                        .nadi(bioData.getNadi())
                        .bloodGroup(bioData.getBloodGroup())
                        .qualification(bioData.getQualification())
                        .job(bioData.getJob())
                        .familyInformation(bioData.getFamilyInformation())
                        .address(bioData.getAddress())
                        .mamkul(bioData.getMamkul())
                        .picUrl(bioData.getPicUrl())
                        .build()).toList();

    }

    @Override
    public BioDataDao show(String id,boolean isApprove) throws BioDataNotFoundException {
        BioData bioData = bioDatRepo.findByIdAndIsApprove(id, isApprove).orElseThrow(() -> new BioDataNotFoundException("Bio-data is not exist"));

        return BioDataDao.builder()
                .id(bioData.getId())
                .fullName(bioData.getFullName())
                .phoneNumber(bioData.getPhoneNumber())
                .alternateNumber(bioData.getAlternateNumber())
                .birthDate(bioData.getBirthDate())
                .birthPlace(bioData.getBirthPlace())
                .height(bioData.getHeight())
                .gotra(bioData.getGotra())
                .gan(bioData.getGan())
                .nstrakhan(bioData.getNstrakhan())
                .nadi(bioData.getNadi())
                .bloodGroup(bioData.getBloodGroup())
                .qualification(bioData.getQualification())
                .job(bioData.getJob())
                .familyInformation(bioData.getFamilyInformation())
                .address(bioData.getAddress())
                .mamkul(bioData.getMamkul())
                .picUrl(bioData.getPicUrl())
                .build();
    }

    @Override
    public boolean isPresent(String id) throws BioDataNotFoundException {
        return bioDatRepo.findByIdAndIsApprove(id, true).isPresent();
    }

    @Override
    public BioDataDao showByUsername(String username) throws BioDataNotFoundException {
        BioData bioData = bioDatRepo.findByUsername(username).orElseThrow(() -> new BioDataNotFoundException("Bio-data is not exist"));

        return BioDataDao.builder()
                .id(bioData.getId())
                .username(bioData.getUsername())
                .fullName(bioData.getFullName())
                .phoneNumber(bioData.getPhoneNumber())
                .alternateNumber(bioData.getAlternateNumber())
                .birthDate(bioData.getBirthDate())
                .birthPlace(bioData.getBirthPlace())
                .height(bioData.getHeight())
                .gotra(bioData.getGotra())
                .gan(bioData.getGan())
                .nstrakhan(bioData.getNstrakhan())
                .nadi(bioData.getNadi())
                .bloodGroup(bioData.getBloodGroup())
                .qualification(bioData.getQualification())
                .job(bioData.getJob())
                .familyInformation(bioData.getFamilyInformation())
                .address(bioData.getAddress())
                .mamkul(bioData.getMamkul())
                .picUrl(bioData.getPicUrl())
                .paymentUrl(bioData.getPaymentUrl())
                .comment(bioData.getComment())
                .registerAt(bioData.getRegisterAt())
                .isUpdate(bioData.isUpdate())
                .isApprove(bioData.isApprove())
                .build();
    }

    @Override
    public BioDataDao add(BioDataDao bioDataDao, String username) throws BioDataAlreadyExistException {

        if (bioDatRepo.findByUsername(username).isPresent()) {
            throw new BioDataAlreadyExistException("Bio-data is already with this username exist");
        } else {
            BioData bioData = BioData.builder()
                    .id(bioDataDao.getId())
                    .username(bioDataDao.getUsername())
                    .fullName(bioDataDao.getFullName())
                    .phoneNumber(bioDataDao.getPhoneNumber())
                    .alternateNumber(bioDataDao.getAlternateNumber())
                    .birthDate(bioDataDao.getBirthDate())
                    .birthPlace(bioDataDao.getBirthPlace())
                    .height(bioDataDao.getHeight())
                    .gotra(bioDataDao.getGotra())
                    .gan(bioDataDao.getGan())
                    .nstrakhan(bioDataDao.getNstrakhan())
                    .nadi(bioDataDao.getNadi())
                    .bloodGroup(bioDataDao.getBloodGroup())
                    .qualification(bioDataDao.getQualification())
                    .job(bioDataDao.getJob())
                    .familyInformation(bioDataDao.getFamilyInformation())
                    .address(bioDataDao.getAddress())
                    .mamkul(bioDataDao.getMamkul())
                    .picUrl(bioDataDao.getPicUrl())
                    .paymentUrl(bioDataDao.getPaymentUrl())
                    .comment("Application Submitted! it will review & approved soon...")
                    .isUpdate(false)
                    .isApprove(false)
                    .build();

            bioData = bioDatRepo.save(bioData);
            return BioDataDao.builder()
                    .id(bioData.getId())
                    .username(bioData.getUsername())
                    .fullName(bioData.getFullName())
                    .phoneNumber(bioData.getPhoneNumber())
                    .alternateNumber(bioData.getAlternateNumber())
                    .birthDate(bioData.getBirthDate())
                    .birthPlace(bioData.getBirthPlace())
                    .height(bioData.getHeight())
                    .gotra(bioData.getGotra())
                    .gan(bioData.getGan())
                    .nstrakhan(bioData.getNstrakhan())
                    .nadi(bioData.getNadi())
                    .bloodGroup(bioData.getBloodGroup())
                    .qualification(bioData.getQualification())
                    .job(bioData.getJob())
                    .familyInformation(bioData.getFamilyInformation())
                    .address(bioData.getAddress())
                    .mamkul(bioData.getMamkul())
                    .picUrl(bioData.getPicUrl())
                    .paymentUrl(bioData.getPaymentUrl())
                    .registerAt(bioData.getRegisterAt())
                    .comment(bioData.getComment())
                    .isApprove(bioData.isApprove())
                    .build();


        }
    }

    @Override
    public BioDataDao update(BioDataDao bioDataDao, String username, String id) throws BioDataNotFoundException, BioDataAlreadyApproveException, BioDataUpdateException {
        BioData bioData = bioDatRepo.findByIdAndUsername(id, username).orElseThrow(() -> new BioDataNotFoundException("Bio-data is not exist"));
        if (!bioData.isUpdate())
            throw new BioDataUpdateException("Your not authorize to update now");

        if (!bioData.isApprove()) {
            bioData.setFullName(bioDataDao.getFullName());
            bioData.setPhoneNumber(bioDataDao.getPhoneNumber());
            bioData.setAlternateNumber(bioDataDao.getAlternateNumber());
            bioData.setBirthDate(bioDataDao.getBirthDate());
            bioData.setBirthPlace(bioDataDao.getBirthPlace());
            bioData.setHeight(bioDataDao.getHeight());
            bioData.setGotra(bioDataDao.getGotra());
            bioData.setGan(bioDataDao.getGan());
            bioData.setNstrakhan(bioDataDao.getNstrakhan());
            bioData.setNadi(bioDataDao.getNadi());
            bioData.setBloodGroup(bioDataDao.getBloodGroup());
            bioData.setQualification(bioDataDao.getQualification());
            bioData.setJob(bioDataDao.getJob());
            bioData.setFamilyInformation(bioDataDao.getFamilyInformation());
            bioData.setAddress(bioDataDao.getAddress());
            bioData.setMamkul(bioDataDao.getMamkul());
            bioData.setPicUrl(bioDataDao.getPicUrl());
            bioData.setPaymentUrl(bioDataDao.getPaymentUrl());
            bioData.setComment("Application Updated! it will review & approved soon...");
            bioData.setUpdate(false);
            bioData = bioDatRepo.save(bioData);
            return BioDataDao.builder()
                    .id(bioData.getId())
                    .username(bioData.getUsername())
                    .fullName(bioData.getFullName())
                    .phoneNumber(bioData.getPhoneNumber())
                    .alternateNumber(bioData.getAlternateNumber())
                    .birthDate(bioData.getBirthDate())
                    .birthPlace(bioData.getBirthPlace())
                    .height(bioData.getHeight())
                    .gotra(bioData.getGotra())
                    .gan(bioData.getGan())
                    .nstrakhan(bioData.getNstrakhan())
                    .nadi(bioData.getNadi())
                    .bloodGroup(bioData.getBloodGroup())
                    .qualification(bioData.getQualification())
                    .job(bioData.getJob())
                    .familyInformation(bioData.getFamilyInformation())
                    .address(bioData.getAddress())
                    .mamkul(bioData.getMamkul())
                    .picUrl(bioData.getPicUrl())
                    .paymentUrl(bioData.getPaymentUrl())
                    .registerAt(bioData.getRegisterAt())
                    .comment(bioData.getComment())
                    .isApprove(bioData.isApprove())
                    .build();
        }
        throw new BioDataAlreadyApproveException("Bio data is already approved");
    }

    @Override
    public boolean delete(String id) {
        bioDatRepo.deleteById(id);
        return true;
    }
}
