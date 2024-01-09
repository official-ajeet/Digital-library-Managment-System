package com.example.digitallibrary.controller;

import com.example.digitallibrary.dto.CreateStudentRequest;
import com.example.digitallibrary.dto.StudentResponse;
import com.example.digitallibrary.dto.UpdateStudentRequest;
import com.example.digitallibrary.models.SecuredUser;
import com.example.digitallibrary.models.Student;
import com.example.digitallibrary.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @PostMapping("")
    public Student createStudent(@RequestBody @Valid CreateStudentRequest createStudentRequest){
        return studentService.create(createStudentRequest);
    }

    @GetMapping("/{studentId}")//this api can ve invoked by admin only
    public Student getStudentForAdmin(@PathVariable("studentId") int studentId){
        /*getting security context - like application context (ioc container) - responsible to hold the info of beans but
        security context - holds the information about the users and credentials , session related info also
        from a class SecurityContextHolder we are getting security context of the current logged-in user who is making the request
        - on the basis of j session id we are able to retrieve the details */
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        SecuredUser securedUser = (SecuredUser)authentication.getPrincipal();

        return studentService.get(studentId);
    }
    @GetMapping("/details")//this api returns the student details who is calling it
    public StudentResponse getStudent(){
        //to get the student id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecuredUser securedUser = (SecuredUser) authentication.getPrincipal();

        int studentId = securedUser.getStudent().getId();
        return studentService.getUsingCache(studentId);
    }

    @DeleteMapping("")// a student can delete himself by entering valid credentials
    public StudentResponse deleteStudent(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecuredUser securedUser = (SecuredUser) authentication.getPrincipal();
        int studentId = securedUser.getStudent().getId();
        return StudentResponse.from(studentService.delete(studentId));
    }

    @PutMapping("")//only a student can update his name and contact of himself only
    public Student Student(@RequestBody @Valid UpdateStudentRequest updateStudentRequest){//@PathVariable("studentId") int studentId,
        //to get the student id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecuredUser securedUser = (SecuredUser) authentication.getPrincipal();
        int studentId = securedUser.getStudent().getId();
        return studentService.update(studentId, updateStudentRequest);
    }
}
