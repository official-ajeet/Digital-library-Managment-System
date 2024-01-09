package com.example.digitallibrary.service;

import com.example.digitallibrary.dto.CreateStudentRequest;
import com.example.digitallibrary.dto.StudentResponse;
import com.example.digitallibrary.dto.UpdateStudentRequest;
import com.example.digitallibrary.models.SecuredUser;
import com.example.digitallibrary.models.Student;
import com.example.digitallibrary.repository.StudentCacheRepository;
import com.example.digitallibrary.repository.StudentRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;

    @Value("${student.authorities}")
    String authorities;

    @Autowired
    StudentCacheRepository studentCacheRepository;

    public Student create(CreateStudentRequest createStudentRequest){
        Student student = createStudentRequest.to();//doing conversion from request to student
        SecuredUser securedUser = student.getSecuredUser();
        securedUser.setPassword(passwordEncoder.encode(securedUser.getPassword()));
        securedUser.setAuthorities(authorities);

        //create a user
        securedUser = userService.save(securedUser);

        //create a student
        student.setSecuredUser(securedUser);

        return studentRepository.save(student);
    }

    /*
    thought process of storing data
            1. either you insert in the cache as soon you insert entry in the db so that next time, you can start seeing the data next time
            2. don't insert at that time, while retrieving first time , do from db then after start retrieving from cache - used approach
     */

    //not in used right now , - it was to fetch from db
    public Student get(int studentId) {
        return studentRepository.findById(studentId).orElse(null);
    }

    public StudentResponse getUsingCache(int studentId) {
        long start = System.currentTimeMillis();
        StudentResponse studentResponse = studentCacheRepository.get(studentId); // 1 hour
        if(studentResponse == null){
            Student student = studentRepository.findById(studentId).orElse(null);
            studentResponse = new StudentResponse(student);
            long end = System.currentTimeMillis();
            System.out.println("Inside get function: Time taken to retrieve the data - " + (end - start));
            studentCacheRepository.set(studentResponse); // this call can be made in parallel
        }else{
            long end = System.currentTimeMillis();
            System.out.println("Inside get function: Time taken to retrieve the data - " + (end - start));

        }

        return studentResponse;
    }

    public Student delete(int studentId) {
        Student student = this.get(studentId);
        studentRepository.deleteById(studentId);//its return type is void
        return student;
    }


    public Student update(int studentId, UpdateStudentRequest updateStudentRequest) {
        Student existingStudent = studentRepository.findById(studentId).orElse(null);
        existingStudent.setName(updateStudentRequest.getName());
        existingStudent.setContact(updateStudentRequest.getContact());
        studentRepository.save(existingStudent);
        return existingStudent;
    }
}
