package com.matrimony.admin.masters.service;

import com.matrimony.admin.masters.dao.MasterDao;
import com.matrimony.admin.masters.exceptions.MasterAttributesAlreadyExitException;
import com.matrimony.admin.masters.exceptions.MasterAttributesNotFoundException;

import java.util.List;

public interface MasterService {
    MasterDao add(MasterDao masterDao) throws MasterAttributesAlreadyExitException;

    MasterDao show(String key) throws MasterAttributesNotFoundException;

    List<MasterDao> showAll();

    MasterDao update(MasterDao masterDao) throws MasterAttributesNotFoundException;

}
