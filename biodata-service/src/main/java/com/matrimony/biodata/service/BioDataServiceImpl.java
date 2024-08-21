package com.matrimony.biodata.service;

import com.matrimony.biodata.enums.FileType;
import com.matrimony.biodata.customExceptions.exceptions.BioDataNotFoundException;
import com.matrimony.biodata.customExceptions.exceptions.BioDataLockException;
import com.matrimony.biodata.dao.BioDataDao;
import com.matrimony.biodata.model.BioData;
import com.matrimony.biodata.repo.BioDatRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BioDataServiceImpl implements BioDataService {
    @Value("${biodata.profile.pic.path}")
    private String profilePic;
    @Value("${biodata.payment.pic.path}")
    private String paymentPic;
    @Value("${biodata.other.doc.path}")
    private String otherDoc;

    @Autowired
    private BioDatRepo bioDatRepo;
    @Autowired
    private AsyncService asyncServices;
    @Autowired
    private FileService fileService;


    @Override
    public BioDataDao approve(String id, String username) throws BioDataNotFoundException {
        BioData bioData = bioDatRepo.findByIdAndIsLock(id, true).orElseThrow(() -> new BioDataNotFoundException("Bio-data is not exist"));

        bioData.setComment("Bio Data Application  approved! on :: " + new Date());
        bioData.setApprove(true);
        bioData.setOperationsBy(username);
        bioData = bioDatRepo.save(bioData);
        log.info("=======================  ${} BIODATA Is APPROVE BY ${} ===================================", bioData.getFullName(), bioData.getOperationsBy());
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

    @Override
    public BioDataDao shouldUpdate(boolean lock, String id, String comments, String username) throws BioDataNotFoundException {
        BioData bioData = bioDatRepo.findById(id).orElseThrow(() -> new BioDataNotFoundException("Bio-data is not exist"));
        if (!lock)
            bioData.setApprove(false);
        bioData.setLock(lock);
        bioData.setComment(comments);
        bioData.setOperationsBy(username);
        bioData = bioDatRepo.save(bioData);
        log.info("=======================  ${} BIODATA SHOULD UPDATE ${} ===================================", bioData.getFullName(), bioData.getComment());
        log.info("============================  END SHOULD UPDATE ==========================================");
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
                .isLock(bioData.isLock())
                .isApprove(bioData.isApprove())
                .build();
    }

    @Override
    public List<BioDataDao> show(boolean isApprove) {
        return bioDatRepo.findAllByIsApproveAndIsLock(isApprove, true)
                .stream().map(bioData -> BioDataDao.builder()
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
                        .build()).toList();

    }

    @Override
    public BioDataDao show(String id, boolean isApprove) throws BioDataNotFoundException {
        BioData bioData = bioDatRepo.findByIdAndIsLockAndIsApprove(id, true, isApprove).orElseThrow(() -> new BioDataNotFoundException("Bio-data is not exist"));

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
                .isLock(bioData.isLock())
                .isApprove(bioData.isApprove())
                .build();
    }

    @Override
    public BioDataDao submitted(String username) throws BioDataNotFoundException {
        BioData bioData = bioDatRepo.findByUsername(username).orElseThrow(() -> new BioDataNotFoundException("Bio-data is not exist"));
        bioData.setComment("Application Submitted! it will review & approved soon...");
        bioData.setLock(true);
        log.debug("Async call save from submitted ");
        asyncServices.save(bioData);
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
                .paymentUrl(bioData.getPaymentUrl())
                .picUrl(bioData.getPicUrl())
                .registerAt(bioData.getRegisterAt())
                .comment(bioData.getComment())
                .isLock(bioData.isLock())
                .isApprove(bioData.isApprove())
                .build();

    }

    @Override
    public BioDataDao add(BioDataDao bioDataDao, String username) throws BioDataLockException {

        Optional<BioData> bioData = bioDatRepo.findByUsername(username);
        if (bioData.isPresent()) {
            if (bioData.get().isLock())
                throw new BioDataLockException("Can't be edit now!");
            bioDataDao.setId(bioData.get().getId());
        }

        BioData data = BioData.builder()
                .id(bioDataDao.getId())
                .username(username)
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
//                .comment("Application Submitted! it will review & approved soon...")
                .isLock(false)
                .isApprove(false)
                .registerAt(new Date())
                .build();

        log.debug("Async call for save");
        asyncServices.save(data);
        log.info("============================${}=========================================", data.getFullName());
        log.info(data.toString());
        log.info("============================End=========================================");
        return BioDataDao.builder()
                .id(data.getId())
                .username(data.getUsername())
                .fullName(data.getFullName())
                .phoneNumber(data.getPhoneNumber())
                .alternateNumber(data.getAlternateNumber())
                .birthDate(data.getBirthDate())
                .birthPlace(data.getBirthPlace())
                .height(data.getHeight())
                .gotra(data.getGotra())
                .gan(data.getGan())
                .nstrakhan(data.getNstrakhan())
                .nadi(data.getNadi())
                .bloodGroup(data.getBloodGroup())
                .qualification(data.getQualification())
                .job(data.getJob())
                .familyInformation(data.getFamilyInformation())
                .address(data.getAddress())
                .mamkul(data.getMamkul())
                .paymentUrl(data.getPaymentUrl())
                .picUrl(data.getPicUrl())
                .registerAt(data.getRegisterAt())
                .comment(data.getComment())
                .isApprove(data.isApprove())
                .build();


    }


    @Override
    @Transactional
    public boolean delete(String id) throws BioDataNotFoundException {
        bioDatRepo.findById(id).orElseThrow(() -> new BioDataNotFoundException("Bio-data is not exist"));
        bioDatRepo.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public long deleteOld(Date date) throws BioDataNotFoundException {
        log.debug("Async call deleteByRegisterAtBefore!");
        long count = bioDatRepo.countByRegisterAtBefore(date);
        asyncServices.deleteByRegisterAtBefore(date);
        return count;
    }

    @Override
    public Page<BioDataDao> show(boolean isApprove, int pageNo, int pageSize, String sort, String filter) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sort));

        return bioDatRepo.findByIsApproveAndFullNameContainsIgnoreCaseOrQualificationContainsIgnoreCaseOrAddressContainsIgnoreCaseOrJobContainsIgnoreCase(pageable, isApprove, filter, filter, filter, filter)
                .map(bioData -> BioDataDao.builder()
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
                        .build());

    }

    @Override
    public void uploadFile(MultipartFile file, String username, FileType fileType) throws IOException, BioDataNotFoundException, BioDataLockException {

        BioData bioData = bioDatRepo.findByUsername(username).orElseThrow(() -> new BioDataNotFoundException("Bio-data is not exist"));
        if (bioData.isLock())
            throw new BioDataLockException("Can't be edit now!");


        String fileName = username + getExtensionByStringHandling(file.getOriginalFilename()).get();
        switch (fileType) {
            case PROFILE:
                bioData.setPicUrl(fileName);
                fileService.uploadFile(profilePic, fileName, file);
                bioDatRepo.save(bioData);
                break;
            case PAYMENT:
                bioData.setPaymentUrl(fileName);
                fileService.uploadFile(paymentPic, fileName, file);
                bioDatRepo.save(bioData);
                break;
        }

    }

    @Override
    public InputStream getResource(String filename, FileType fileType) throws FileNotFoundException {
        return switch (fileType) {
            case PROFILE -> fileService.getResource(profilePic, filename);
            case PAYMENT -> fileService.getResource(paymentPic, filename);
            default -> fileService.getResource(otherDoc, filename);
        };
    }

    public Optional<String> getExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

}
