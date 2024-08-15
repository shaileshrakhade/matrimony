package com.matrimony.admin.masters.controller;

import com.matrimony.admin.masters.dao.MasterDao;
import com.matrimony.admin.masters.exceptions.MasterAttributesAlreadyExitException;
import com.matrimony.admin.masters.exceptions.MasterAttributesNotFoundException;
import com.matrimony.admin.masters.service.MasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/master-attribute/admin/")
public class MasterAdminController {

    @Autowired
    private MasterService masterService;

    @PostMapping("add")
    @ResponseStatus(HttpStatus.CREATED)
    public MasterDao add(@RequestBody MasterDao MasterDao) throws MasterAttributesAlreadyExitException {
        return masterService.add(MasterDao);
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

}
