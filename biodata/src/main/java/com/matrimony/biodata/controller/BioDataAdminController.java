package com.matrimony.biodata.controller;


import com.matrimony.biodata.customExceptions.exceptions.BioDataNotFoundException;
import com.matrimony.biodata.dao.BioDataDao;
import com.matrimony.biodata.service.BioDataService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/bio-data/")
public class BioDataAdminController {

    private final BioDataService bioDataService;
//    private final JwtService jwtService;

    public BioDataAdminController(@Qualifier("bioDataServiceImpl") BioDataService bioDataService) {
        this.bioDataService = bioDataService;
    }

    @GetMapping("should-approve")
    @ResponseStatus(HttpStatus.OK)
    public List<BioDataDao> show() {
        return bioDataService.show(false);
    }

    @GetMapping("should-approve/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BioDataDao show(@PathVariable("id") String id) throws BioDataNotFoundException {
        return bioDataService.show(id, false);
    }

    @PutMapping("should-update/{isUpdate}/{id}/{comments}")
    @ResponseStatus(HttpStatus.OK)
    public boolean shouldUpdate(HttpServletRequest request, @PathVariable("isUpdate") boolean isUpdate, @PathVariable("id") String id, @PathVariable("comments") String comments) throws BioDataNotFoundException {
//        String username = jwtService.extractUsername(request.getHeader("Authorization"));
        return bioDataService.shouldUpdate(isUpdate, id, comments, "username");
    }

    @PutMapping("approve/{isApprove}/{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean Approve(HttpServletRequest request, @PathVariable("isApprove") boolean isApprove, @PathVariable("id") String id) throws BioDataNotFoundException {

//        String username = jwtService.extractUsername(request.getHeader("Authorization"));
        return bioDataService.approve(isApprove, id, "username");
    }

    @DeleteMapping("delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean update(@PathVariable("id") String id) {
        return bioDataService.delete(id);
    }


}
