package com.matrimony.biodata.controller;


import com.matrimony.biodata.customExceptions.exceptions.BioDataNotFoundException;
import com.matrimony.biodata.dao.BioDataDao;
import com.matrimony.biodata.masters.exceptions.MasterAttributesNotFoundException;
import com.matrimony.biodata.service.BioDataService;
import com.matrimony.biodata.util.jwt.JwtClaims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/bio-data/admin/")
@Slf4j
public class BioDataAdminController {

    private final BioDataService bioDataService;
    @Autowired
    private JwtClaims jwtClaims;

    public BioDataAdminController(@Qualifier("bioDataServiceImpl") BioDataService bioDataService) {
        this.bioDataService = bioDataService;
    }


    @GetMapping("not-approved")
    @ResponseStatus(HttpStatus.OK)
    public Page<BioDataDao> notApproved(@RequestParam(value = "filter", required = false, defaultValue = "") String filter,
                                        @RequestParam(value = "sort", required = false, defaultValue = "registerAt") String sort,
                                        @RequestParam(value = "pageNo", required = false, defaultValue = "0") int pageNo,
                                        @RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize) throws BioDataNotFoundException, MasterAttributesNotFoundException {

        return bioDataService.show(false, pageNo, pageSize, sort, filter);
    }

//    @GetMapping("approved")
//    @ResponseStatus(HttpStatus.OK)
//    public List<BioDataDao> showAll() {
//        return bioDataService.show(true);
//    }

    @GetMapping("approved")
    @ResponseStatus(HttpStatus.OK)
    public Page<BioDataDao> approved(@RequestParam(value = "filter", required = false, defaultValue = "") String filter,
                                     @RequestParam(value = "sort", required = false, defaultValue = "registerAt") String sort,
                                     @RequestParam(value = "pageNo", required = false, defaultValue = "0") int pageNo,
                                     @RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize) throws BioDataNotFoundException, MasterAttributesNotFoundException {

        return bioDataService.show(false, pageNo, pageSize, sort, filter);
    }

    @GetMapping("not-approved/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BioDataDao show(@PathVariable("id") String id) throws BioDataNotFoundException {
        return bioDataService.show(id, false);
    }

    @PutMapping("need-update/{is-update}/{id}/{comments}")
    @ResponseStatus(HttpStatus.OK)
    public BioDataDao shouldUpdate(HttpServletRequest request, @PathVariable("lock") boolean lock, @PathVariable("id") String id, @PathVariable("comments") String comments) throws BioDataNotFoundException {
        String username = jwtClaims.extractUsername(request.getHeader(HttpHeaders.AUTHORIZATION));
        return bioDataService.shouldUpdate(lock, id, comments, username);
    }

    @PutMapping("approved/{is-approve}/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BioDataDao Approve(HttpServletRequest request, @PathVariable("id") String id) throws BioDataNotFoundException {
        String username = jwtClaims.extractUsername(request.getHeader(HttpHeaders.AUTHORIZATION));
        return bioDataService.approve(id, username);
    }

    @DeleteMapping("delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean delete(HttpServletRequest request, @PathVariable("id") String id) throws BioDataNotFoundException {
        String username = jwtClaims.extractUsername(request.getHeader(HttpHeaders.AUTHORIZATION));
        log.info("======================= DELETE REQUEST RISE FOR ${} BY ${} ===================================", id, username);
        return bioDataService.delete(id);
    }

    @DeleteMapping("delete-old/{date}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteOld(HttpServletRequest request, @PathVariable("date") @DateTimeFormat(pattern = "MMddyyyy") Date date) throws BioDataNotFoundException {
        String username = jwtClaims.extractUsername(request.getHeader(HttpHeaders.AUTHORIZATION));
        log.info("======================= DELETE OLD BIODATA REQUEST RISE BY ${} DELETE DATA FROM ${} ===================================", username, date);
        return bioDataService.deleteOld(date) + " bio-data was deleted before :: " + date;
    }
}
