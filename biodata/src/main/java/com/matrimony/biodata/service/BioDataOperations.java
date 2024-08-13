package com.matrimony.biodata.service;

import com.matrimony.biodata.customExceptions.exceptions.BioDataAlreadyApproveException;
import com.matrimony.biodata.customExceptions.exceptions.BioDataAlreadyExistException;
import com.matrimony.biodata.customExceptions.exceptions.BioDataNotFoundException;
import com.matrimony.biodata.dao.BioDataDao;

import java.util.List;

public interface BioDataOperations {
    public boolean approve(boolean isApprove,String id) throws BioDataNotFoundException;
    public List<BioDataDao> show();
    public BioDataDao show(String id) throws BioDataNotFoundException;
    public BioDataDao showByUsername(String username) throws BioDataNotFoundException;
    public BioDataDao add(BioDataDao bioDataDao,String username) throws BioDataAlreadyExistException;
    public BioDataDao update(BioDataDao bioDataDao,String username,String id) throws BioDataNotFoundException, BioDataAlreadyApproveException;
    public boolean delete(String id);
}
