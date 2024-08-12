package com.matrimony.biodata.controller;


import com.matrimony.biodata.customExceptions.exceptions.BioDataNotFoundException;
import com.matrimony.biodata.dao.BioDataDao;
import com.matrimony.biodata.service.BioDataOperations;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/biodata/")
public class AdminController {

    @Autowired
    private BioDataOperations bioData;

    @GetMapping("show/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BioDataDao show(@PathVariable("id") String id) throws BioDataNotFoundException {
        return bioData.show(id);
    }

    @PutMapping("approve/{id}/{isApprove}")
    public boolean Approve(@PathVariable("id") String id, @PathVariable("id") boolean isApprove) throws BioDataNotFoundException {
        return bioData.approve(id, isApprove);
    }

}
