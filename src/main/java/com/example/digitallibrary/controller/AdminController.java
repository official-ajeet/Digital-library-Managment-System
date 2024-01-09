package com.example.digitallibrary.controller;

import com.example.digitallibrary.dto.CreateAdminRequest;
import com.example.digitallibrary.models.Admin;
import com.example.digitallibrary.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AdminController {

    @Autowired
    AdminService adminService;

    @PostMapping("/admin")
    public Admin createAdmin(@RequestBody @Valid CreateAdminRequest createAdminRequest){
        return adminService.create(createAdminRequest);
    }
}
