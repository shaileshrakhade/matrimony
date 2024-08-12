package com.matrimony.biodata.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/")
public class AdminController {

    @GetMapping("show-biodata/{id}")
    public String biodataShow(@RequestParam String id) {
        return "see bigdata :: "+id;
    }

    @PutMapping("approve-biodata")
    public String biodataApprove() {
        return "show bigdata on dashboard";
    }

    @PutMapping("publish-biodata")
    public String biodataPublish() {
        return "show bigdata on dashboard";
    }
}
