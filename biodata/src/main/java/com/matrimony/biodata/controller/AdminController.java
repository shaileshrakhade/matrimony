package com.matrimony.biodata.controller;


import com.matrimony.biodata.customExceptions.exceptions.BioDataNotFoundException;
import com.matrimony.biodata.service.BioDataOperations;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/bio-data/")
public class AdminController {

    private final BioDataOperations bioData;

    public AdminController(@Qualifier("bioDataService") BioDataOperations bioData) {
        this.bioData = bioData;
    }


    @PutMapping("approve/{isApprove}/{id}")
    public boolean Approve(@PathVariable("isApprove") boolean isApprove,@PathVariable("id") String id) throws BioDataNotFoundException {
        return bioData.approve(isApprove, id);
    }

    @DeleteMapping("delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean update(@PathVariable("id") String id) {
        return bioData.delete(id);
    }

}
