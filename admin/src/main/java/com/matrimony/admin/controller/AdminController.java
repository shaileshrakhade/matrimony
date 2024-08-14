package com.matrimony.admin.controller;

import com.matrimony.admin.customExceptions.exceptions.AdminAttributesNotFoundException;
import com.matrimony.admin.dao.AdminDao;
import com.matrimony.admin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/attributes/")
public class AdminController {

    @Autowired
    private AdminService adminService;


    @PostMapping("add")
    @ResponseStatus(HttpStatus.CREATED)
    public AdminDao add(@RequestBody AdminDao adminDao) {
        return adminService.add(adminDao);
    }

    @PutMapping("update")
    @ResponseStatus(HttpStatus.OK)
    public AdminDao update(@RequestBody AdminDao adminDao) throws AdminAttributesNotFoundException {
        return adminService.update(adminDao);
    }

    @PutMapping("make-publish/{publish}")
    @ResponseStatus(HttpStatus.OK)
    public AdminDao makePublish(@RequestParam("publish") boolean isPublish) throws AdminAttributesNotFoundException {
        return adminService.makePublish(isPublish);
    }

    @GetMapping("show/{key}")
    @ResponseStatus(HttpStatus.OK)
    public AdminDao show(@RequestParam("key") String key) throws AdminAttributesNotFoundException {
        return adminService.show(key);
    }

    @GetMapping("show-all")
    @ResponseStatus(HttpStatus.OK)
    public List<AdminDao> showAll(){
        return adminService.showAll();
    }

    @GetMapping("bio-data-pdf-url")
    @ResponseStatus(HttpStatus.OK)
    public AdminDao bioDataPdfUrl() throws AdminAttributesNotFoundException {
        return adminService.bioDataPdfUrl();
    }

    @GetMapping("dashboard-message")
    @ResponseStatus(HttpStatus.OK)
    public AdminDao dashboardMessage() throws AdminAttributesNotFoundException {
        return adminService.dashBoardMessage();
    }

    @GetMapping("notification-message")
    @ResponseStatus(HttpStatus.OK)
    public AdminDao notificationMessage() throws AdminAttributesNotFoundException {
        return adminService.notificationMessage();
    }

    @GetMapping("is-publish")
    @ResponseStatus(HttpStatus.OK)
    public boolean isPublish() throws AdminAttributesNotFoundException {
        return adminService.isPublish();
    }
}
