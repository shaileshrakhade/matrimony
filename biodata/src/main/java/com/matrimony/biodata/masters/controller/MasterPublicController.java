package com.matrimony.biodata.masters.controller;

import com.matrimony.biodata.masters.dao.MasterDao;
import com.matrimony.biodata.masters.exceptions.MasterAttributesNotFoundException;
import com.matrimony.biodata.masters.service.MasterService;
import com.matrimony.biodata.masters.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bio-data/master-attribute/")
public class MasterPublicController {

    @Autowired
    private MasterService masterService;

    @GetMapping("show/{key}")
    @ResponseStatus(HttpStatus.OK)
    public MasterDao show(@RequestParam("key") String key) throws MasterAttributesNotFoundException {
        return masterService.show(key);
    }

    @GetMapping("is-publish")
    @ResponseStatus(HttpStatus.OK)
    public boolean isPublish() throws MasterAttributesNotFoundException {
        return masterService.isPublish();
    }

}
