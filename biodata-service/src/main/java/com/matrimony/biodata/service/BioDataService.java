package com.matrimony.biodata.service;

import com.matrimony.biodata.enums.FileType;
import com.matrimony.biodata.customExceptions.exceptions.BioDataAlreadyExistException;
import com.matrimony.biodata.customExceptions.exceptions.BioDataLockException;
import com.matrimony.biodata.customExceptions.exceptions.BioDataNotFoundException;
import com.matrimony.biodata.dao.BioDataDao;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

public interface BioDataService {
    public BioDataDao approve(String id, String username) throws BioDataNotFoundException;

    public BioDataDao shouldUpdate(boolean lock, String id, String comments, String username) throws BioDataNotFoundException;

    public List<BioDataDao> show(boolean isApprove);

    public BioDataDao show(String id, boolean isApprove) throws BioDataNotFoundException;

    public boolean isPresent(String id) throws BioDataNotFoundException;

    public BioDataDao showByUsername(String username) throws BioDataNotFoundException;

    public BioDataDao submitted(String username) throws BioDataNotFoundException;

    public BioDataDao add(BioDataDao bioDataDao, String username) throws BioDataAlreadyExistException, IOException, BioDataLockException;


    public boolean delete(String id) throws BioDataNotFoundException;

    public long deleteOld(Date date) throws BioDataNotFoundException;


    Page<BioDataDao> show(boolean b, int pageNo, int pageSize, String sort, String filter);

    void uploadFile(MultipartFile file, String username, FileType fileType) throws IOException, BioDataNotFoundException, BioDataLockException;

    InputStream getResource(String filename, FileType fileType) throws BioDataNotFoundException, FileNotFoundException;
}
