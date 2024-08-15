package com.matrimony.admin.masters.controller;

import com.matrimony.admin.masters.dao.MasterDao;
import com.matrimony.admin.masters.exceptions.MasterAttributesNotFoundException;
import com.matrimony.admin.masters.service.MasterService;
import com.matrimony.admin.masters.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/master-attribute/")
public class MasterPublicController {

    @Autowired
    private MasterService masterService;

    @GetMapping("show/{key}")
    @ResponseStatus(HttpStatus.OK)
    public MasterDao show(@RequestParam("key") String key) throws MasterAttributesNotFoundException {
        return masterService.show(key);
    }

    @GetMapping("bio-data-pdf-url")
    @ResponseStatus(HttpStatus.OK)
    public MasterDao bioDataPdfUrl() throws MasterAttributesNotFoundException {
        return masterService.show(Constants.BIO_DATA_PDF_FILE_URL);
    }

    @GetMapping("dashboard-message")
    @ResponseStatus(HttpStatus.OK)
    public MasterDao dashboardMessage() throws MasterAttributesNotFoundException {
        return masterService.show(Constants.DASHBOARD_MESSAGE);
    }

    @GetMapping("notification-message")
    @ResponseStatus(HttpStatus.OK)
    public MasterDao notificationMessage() throws MasterAttributesNotFoundException {
        return masterService.show(Constants.DEFAULT_NOTIFICATION);
    }

}
