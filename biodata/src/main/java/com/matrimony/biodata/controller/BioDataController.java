package com.matrimony.biodata.controller;

import com.matrimony.biodata.customExceptions.exceptions.BioDataAlreadyApproveException;
import com.matrimony.biodata.customExceptions.exceptions.BioDataAlreadyExistException;
import com.matrimony.biodata.customExceptions.exceptions.BioDataNotFoundException;
import com.matrimony.biodata.dao.BioDataDao;
import com.matrimony.biodata.service.BioDataOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bio-data/")
public class BioDataController {

    @Autowired
    @Qualifier("bioDataService")
    private BioDataOperations bioData;

    @GetMapping("show")
    @ResponseStatus(HttpStatus.OK)
    public List<BioDataDao> show() throws BioDataNotFoundException {
        return bioData.show();
    }

    @GetMapping("show/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BioDataDao show(@PathVariable("id") String id) throws BioDataNotFoundException {
        return bioData.show(id);
    }

    @GetMapping("{username}")
    @ResponseStatus(HttpStatus.OK)
    public BioDataDao myBioData(@PathVariable("username") String username) throws BioDataNotFoundException {
        return bioData.showByUsername(username);
    }

    @PostMapping("add/{username}")
    @ResponseStatus(HttpStatus.CREATED)
    public BioDataDao add(@RequestBody BioDataDao bioDataDao, @PathVariable("username") String username) throws BioDataAlreadyExistException {
        return bioData.add(bioDataDao, username);
    }

    @PutMapping("update/{username}/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BioDataDao update(@RequestBody BioDataDao bioDataDao, @PathVariable("id") String id, @PathVariable("username") String username) throws BioDataAlreadyApproveException, BioDataNotFoundException {
        return bioData.update(bioDataDao, username, id);
    }


}
