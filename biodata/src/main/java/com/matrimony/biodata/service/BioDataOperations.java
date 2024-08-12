package com.matrimony.biodata.service;

import com.matrimony.biodata.customExceptions.exceptions.BioDataAlreadyApproveException;
import com.matrimony.biodata.customExceptions.exceptions.BioDataNotFoundException;
import com.matrimony.biodata.dao.BioDataDao;

public interface BioDataOperations {
    public boolean approve(String id,boolean isApprove) throws BioDataNotFoundException;
    public BioDataDao show(String id,String username) throws BioDataNotFoundException;
    public BioDataDao show(String id) throws BioDataNotFoundException;
    public String add(BioDataDao bioDataDao);
    public String update(BioDataDao bioDataDao,String id) throws BioDataNotFoundException, BioDataAlreadyApproveException;
}
