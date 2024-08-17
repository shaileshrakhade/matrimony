package com.matrimony.biodata.controller;

import com.matrimony.biodata.customExceptions.exceptions.BioDataAlreadyApproveException;
import com.matrimony.biodata.customExceptions.exceptions.BioDataAlreadyExistException;
import com.matrimony.biodata.customExceptions.exceptions.BioDataNotFoundException;
import com.matrimony.biodata.customExceptions.exceptions.BioDataUpdateException;
import com.matrimony.biodata.dao.BioDataDao;
import com.matrimony.biodata.masters.exceptions.MasterAttributesNotFoundException;
import com.matrimony.biodata.masters.service.MasterService;
import com.matrimony.biodata.masters.util.Constants;
import com.matrimony.biodata.service.BioDataService;
import com.matrimony.biodata.util.jwt.JwtClaims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/bio-data/")
public class BioDataController {

    @Autowired
    @Qualifier("bioDataServiceImpl")
    private BioDataService bioDataService;
    @Autowired
    private MasterService masterService;
    @Autowired
    private JwtClaims jwtClaims;

    @GetMapping("show")
    @ResponseStatus(HttpStatus.OK)
    public Page<BioDataDao> show(@RequestParam(value = "filter", required = false, defaultValue = "") String filter, @RequestParam(value = "sort", required = false, defaultValue = "registerAt") String sort, @RequestParam(value = "pageNo", required = false, defaultValue = "0") int pageNo, @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) throws BioDataNotFoundException, MasterAttributesNotFoundException {
        if (masterService.isPublish()) return bioDataService.show(true, pageNo, pageSize, sort, filter);
        else throw new BioDataNotFoundException(masterService.show(Constants.BIO_DATA_NOT_PUBLISH_MSG).getValue());
    }

    @GetMapping("show/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BioDataDao show(@PathVariable("id") String id) throws BioDataNotFoundException {
        return bioDataService.show(id, true);
    }

    @GetMapping("is-present/{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean isPresent(@PathVariable("id") String id) throws BioDataNotFoundException {
        return bioDataService.isPresent(id);
    }

    @GetMapping("{username}")
    @ResponseStatus(HttpStatus.OK)
    public BioDataDao myBioData(@PathVariable("username") String username) throws BioDataNotFoundException {
        return bioDataService.showByUsername(username);
    }

    @PostMapping("test-add/{username}")
    @ResponseStatus(HttpStatus.CREATED)
    public BioDataDao add(@RequestBody BioDataDao bioDataDao, @PathVariable("username") String username) throws BioDataAlreadyExistException {
        return bioDataService.add(bioDataDao, username);
    }

    @PostMapping("add")
    @ResponseStatus(HttpStatus.CREATED)
    public BioDataDao add(HttpServletRequest request, @RequestBody BioDataDao bioDataDao) throws BioDataAlreadyExistException {
        String tokenUsername = jwtClaims.extractUsername(request.getHeader(HttpHeaders.AUTHORIZATION));
        return bioDataService.add(bioDataDao, tokenUsername);
    }

    @PutMapping("update/{username}/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BioDataDao update(@RequestBody BioDataDao bioDataDao, @PathVariable("id") String id, @PathVariable("username") String username) throws BioDataAlreadyApproveException, BioDataNotFoundException, BioDataUpdateException {
        return bioDataService.update(bioDataDao, username, id);
    }


}
