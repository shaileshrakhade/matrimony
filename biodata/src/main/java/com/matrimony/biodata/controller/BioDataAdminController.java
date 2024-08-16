package com.matrimony.biodata.controller;


import com.matrimony.biodata.customExceptions.exceptions.BioDataNotFoundException;
import com.matrimony.biodata.dao.BioDataDao;
import com.matrimony.biodata.service.BioDataService;
import com.matrimony.biodata.util.jwt.JwtClaims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/bio-data/admin/")
public class BioDataAdminController {

    private final BioDataService bioDataService;
    @Autowired
    private JwtClaims jwtClaims;

    public BioDataAdminController(@Qualifier("bioDataServiceImpl") BioDataService bioDataService) {
        this.bioDataService = bioDataService;
    }

    @GetMapping("not-approved")
    @ResponseStatus(HttpStatus.OK)
    public List<BioDataDao> show() {
        return bioDataService.show(false);
    }

    @GetMapping("approved")
    @ResponseStatus(HttpStatus.OK)
    public List<BioDataDao> showAll() {
        return bioDataService.show(true);
    }

    @GetMapping("not-approved/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BioDataDao show(@PathVariable("id") String id) throws BioDataNotFoundException {
        return bioDataService.show(id, false);
    }

    @PutMapping("need-update/{is-update}/{id}/{comments}")
    @ResponseStatus(HttpStatus.OK)
    public BioDataDao shouldUpdate(HttpServletRequest request, @PathVariable("is-update") boolean isUpdate, @PathVariable("id") String id, @PathVariable("comments") String comments) throws BioDataNotFoundException {
        String username = jwtClaims.extractUsername(request.getHeader("Authorization"));
        return bioDataService.shouldUpdate(isUpdate, id, comments, username);
    }

    @PutMapping("approved/{is-approve}/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BioDataDao Approve(HttpServletRequest request, @PathVariable("is-approve") boolean isApprove, @PathVariable("id") String id) throws BioDataNotFoundException {
        String username = jwtClaims.extractUsername(request.getHeader("Authorization"));
        return bioDataService.approve(isApprove, id, username);
    }

    @DeleteMapping("delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean delete(@PathVariable("id") String id) throws BioDataNotFoundException {
        return bioDataService.delete(id);
    }

    @DeleteMapping("delete-old/{date}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteOld(@PathVariable("date") @DateTimeFormat(pattern = "MMddyyyy") Date date) throws BioDataNotFoundException {
        return bioDataService.deleteOld(date) + " bio-data was deleted before :: " + date;
    }


}
