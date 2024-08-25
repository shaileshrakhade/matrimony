package com.matrimony.biodata.controller;

import com.matrimony.biodata.customExceptions.exceptions.BioDataRegistrationCloseException;
import com.matrimony.biodata.enums.FileType;
import com.matrimony.biodata.customExceptions.exceptions.BioDataAlreadyExistException;
import com.matrimony.biodata.customExceptions.exceptions.BioDataLockException;
import com.matrimony.biodata.customExceptions.exceptions.BioDataNotFoundException;
import com.matrimony.biodata.dao.BioDataDao;
import com.matrimony.biodata.masters.exceptions.MasterAttributesNotFoundException;
import com.matrimony.biodata.masters.service.MasterService;
import com.matrimony.biodata.masters.util.Constants;
import com.matrimony.biodata.securityConfig.enums.Role;
import com.matrimony.biodata.service.BioDataService;
import com.matrimony.biodata.util.jwt.JwtClaims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/bio-data/")
public class BioDataController {

    @Autowired
    @Qualifier("bioDataServiceImpl")
    private BioDataService bioDataService;
    @Autowired
    private MasterService masterService;
    @Autowired
    private JwtClaims jwtClaims;

    @GetMapping("show")
    @ResponseStatus(HttpStatus.OK)
    public Page<BioDataDao> show(@RequestParam(value = "filter", required = false, defaultValue = "") String filter, @RequestParam(value = "sort", required = false, defaultValue = "registerAt") String sort, @RequestParam(value = "pageNo", required = false, defaultValue = "0") int pageNo, @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) throws BioDataNotFoundException, MasterAttributesNotFoundException {
        if (masterService.isPublish()) return bioDataService.show(true, pageNo, pageSize, sort, filter);
        else throw new BioDataNotFoundException(masterService.show(Constants.BIO_DATA_NOT_PUBLISH_MSG).getValue());
    }

    @GetMapping("show/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BioDataDao show(@PathVariable("id") String id) throws BioDataNotFoundException {
        return bioDataService.show(id, true);
    }

    @GetMapping("is-present/{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean isPresent(@PathVariable("id") String id) throws BioDataNotFoundException {
        return bioDataService.isPresent(id);
    }

    @GetMapping("{username}")
    @ResponseStatus(HttpStatus.OK)
    public BioDataDao myBioData(@PathVariable("username") String username) throws BioDataNotFoundException {
        return bioDataService.showByUsername(username);
    }

    @PostMapping("submitted")
    @ResponseStatus(HttpStatus.CREATED)
    public BioDataDao add(HttpServletRequest request) throws BioDataNotFoundException {
        return bioDataService.submitted(jwtClaims.extractUsername(request.getHeader(HttpHeaders.AUTHORIZATION)));
    }

    @PostMapping("add")
    @ResponseStatus(HttpStatus.CREATED)
    public BioDataDao add(HttpServletRequest request, HttpServletResponse response, @RequestBody BioDataDao bioDataDao) throws BioDataAlreadyExistException, IOException, BioDataLockException, MasterAttributesNotFoundException, BioDataRegistrationCloseException {
        if (masterService.isRegistrationClose())
            throw new BioDataRegistrationCloseException("Registration was Close!");

        String username;
        response.setContentType(MediaType.MULTIPART_FORM_DATA_VALUE);
        if (jwtClaims.extractRoles(request.getHeader(HttpHeaders.AUTHORIZATION)).contains(Role.ADMIN.name()))
            username = bioDataDao.getPhoneNumber();
        else
            username = jwtClaims.extractUsername(request.getHeader(HttpHeaders.AUTHORIZATION));

        return bioDataService.add(bioDataDao, username);
    }

    @PostMapping("upload-file")
    @ResponseStatus(HttpStatus.OK)
    public String uploadFile(HttpServletRequest request, @RequestParam("file") MultipartFile file, @RequestParam("filetype") FileType fileType) throws Exception {
        if (Objects.equals(file.getContentType(), MediaType.IMAGE_JPEG_VALUE)) {
            String username = jwtClaims.extractUsername(request.getHeader(HttpHeaders.AUTHORIZATION));
            bioDataService.uploadFile(file, username, fileType);
            return fileType.name() + " Successfully Updated";
        } else {
            throw new Exception("Only " + MediaType.IMAGE_JPEG_VALUE + " is supported");
        }
    }

    @GetMapping("download-file")
    @ResponseStatus(HttpStatus.OK)
    public void downloadFile(HttpServletResponse response, @RequestParam("filename") String filename, @RequestParam("filetype") FileType fileType) throws IOException, BioDataNotFoundException {
        InputStream inputStream = bioDataService.getResource(filename, fileType);
        if (!fileType.equals(FileType.Other))
            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        else
            response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        StreamUtils.copy(inputStream, response.getOutputStream());
    }
}
