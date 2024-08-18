package com.matrimony.biodata.masters.controller;

import com.matrimony.biodata.masters.dao.MasterDao;
import com.matrimony.biodata.masters.exceptions.MasterAttributesAlreadyExitException;
import com.matrimony.biodata.masters.exceptions.MasterAttributesNotFoundException;
import com.matrimony.biodata.masters.service.MasterService;
import com.matrimony.biodata.masters.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bio-data/admin/master-attribute/")
public class MasterAdminController {

    @Autowired
    private MasterService masterService;

    @PostMapping("add")
    @ResponseStatus(HttpStatus.CREATED)
    public MasterDao add(@RequestBody MasterDao masterDao) throws MasterAttributesAlreadyExitException {
        return masterService.add(masterDao);
    }

    @PutMapping("update")
    @ResponseStatus(HttpStatus.OK)
    public MasterDao update(@RequestBody MasterDao masterDao) throws MasterAttributesNotFoundException {
        return masterService.update(masterDao);
    }

    @GetMapping("show-all")
    @ResponseStatus(HttpStatus.OK)
    public List<MasterDao> showAll() {
        return masterService.showAll();
    }
    @PutMapping("make-publish/{flg}")
    @ResponseStatus(HttpStatus.OK)
    public MasterDao showAll(@PathVariable("flg") boolean flg) throws MasterAttributesNotFoundException {
        MasterDao masterDao = new MasterDao();
        masterDao.setKey(Constants.IS_BIO_DATA_PUBLISH);
        masterDao.setValue(String.valueOf(flg));
        return masterService.update(masterDao);
    }
    @DeleteMapping("delete/{key}")
    @ResponseStatus(HttpStatus.OK)
    public boolean delete(@PathVariable("key") String key) throws MasterAttributesNotFoundException {
        return masterService.delete(key);
    }


}
