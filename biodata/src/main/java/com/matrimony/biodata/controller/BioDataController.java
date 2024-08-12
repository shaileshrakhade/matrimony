package com.matrimony.biodata.controller;

import com.matrimony.biodata.service.BioData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/biodata/")
public class BioDataController {

    @Autowired
    private BioData biodata;

    @GetMapping("show")
    public String show() {
        return "show bigdata";
    }

    @PostMapping("add")
    public String add() {
        return "add bigdata";
    }

    @PutMapping("update")
    public String update() {
        return "update bigdata";
    }
}
