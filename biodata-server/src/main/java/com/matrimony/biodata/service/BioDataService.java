package com.matrimony.biodata.service;

import com.matrimony.biodata.customExceptions.exceptions.BioDataAlreadyApproveException;
import com.matrimony.biodata.customExceptions.exceptions.BioDataAlreadyExistException;
import com.matrimony.biodata.customExceptions.exceptions.BioDataNotFoundException;
import com.matrimony.biodata.customExceptions.exceptions.BioDataUpdateException;
import com.matrimony.biodata.dao.BioDataDao;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

public interface BioDataService {
    public BioDataDao approve(boolean isApprove, String id, String username) throws BioDataNotFoundException;

    public BioDataDao shouldUpdate(boolean isUpdate, String id, String comments, String username) throws BioDataNotFoundException;

    public List<BioDataDao> show(boolean isApprove);

    public BioDataDao show(String id, boolean isApprove) throws BioDataNotFoundException;

    public boolean isPresent(String id) throws BioDataNotFoundException;

    public BioDataDao showByUsername(String username) throws BioDataNotFoundException;

    public BioDataDao add(BioDataDao bioDataDao, String username) throws BioDataAlreadyExistException;

    public BioDataDao update(BioDataDao bioDataDao, String username, String id) throws BioDataNotFoundException, BioDataAlreadyApproveException, BioDataUpdateException;

    public boolean delete(String id) throws BioDataNotFoundException;

    public long deleteOld(Date date) throws BioDataNotFoundException;


    Page<BioDataDao> show(boolean b, int pageNo, int pageSize, String sort, String filter);
}
