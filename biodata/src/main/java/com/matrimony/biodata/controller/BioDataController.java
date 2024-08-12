package com.matrimony.biodata.controller;

import com.matrimony.biodata.customExceptions.exceptions.BioDataAlreadyApproveException;
import com.matrimony.biodata.customExceptions.exceptions.BioDataNotFoundException;
import com.matrimony.biodata.dao.BioDataDao;
import com.matrimony.biodata.service.BioDataOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/biodata/")
public class BioDataController {

    @Autowired
    private BioDataOperations bioData;

    @GetMapping("show/{id}/{username}")
    @ResponseStatus(HttpStatus.OK)
    public BioDataDao show(@PathVariable("id") String id,@PathVariable("username") String username) throws BioDataNotFoundException {
        return bioData.show(id,username);
    }

    @PostMapping("add")
    @ResponseStatus(HttpStatus.CREATED)
    public String add(@RequestBody BioDataDao bioDataDao) {
        return bioData.add(bioDataDao);
    }

    @PutMapping("update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String update(@RequestBody BioDataDao bioDataDao, @PathVariable("id") String id) throws BioDataAlreadyApproveException, BioDataNotFoundException {
        return bioData.update(bioDataDao, id);
    }
}
