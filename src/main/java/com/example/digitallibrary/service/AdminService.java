package com.example.digitallibrary.service;

import com.example.digitallibrary.dto.CreateAdminRequest;
import com.example.digitallibrary.dto.CreateStudentRequest;
import com.example.digitallibrary.models.Admin;
import com.example.digitallibrary.models.SecuredUser;
import com.example.digitallibrary.models.Student;
import com.example.digitallibrary.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;

    @Value("${admin.authorities}")
    String authorities;

    @Autowired
    AdminRepository adminRepository;

    public Admin create(CreateAdminRequest createAdminRequest){
        Admin admin = createAdminRequest.to();//doing conversion from request to student
        SecuredUser securedUser = admin.getSecuredUser();
        securedUser.setPassword(passwordEncoder.encode(securedUser.getPassword()));
        securedUser.setAuthorities(authorities);

        //create a user
        securedUser = userService.save(securedUser);

        //create a student
        admin.setSecuredUser(securedUser);

        return adminRepository.save(admin);
    }
}
