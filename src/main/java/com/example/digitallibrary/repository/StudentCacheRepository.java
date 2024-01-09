package com.example.digitallibrary.repository;

import com.example.digitallibrary.dto.StudentResponse;
import com.example.digitallibrary.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class StudentCacheRepository {
    @Autowired
    RedisTemplate<String , Object> redisTemplate;

    private static final String STUDENT_KEY_PREFIX = "std::";

    public void set(StudentResponse student){//setting in the cache
        System.out.println("Inside set function of cache repository");
        if(student.getId() == 0){
            return ;
        }
        String key = STUDENT_KEY_PREFIX+student.getId();//generate a key first
        redisTemplate.opsForValue().set(key,student,3600, TimeUnit.SECONDS);//storing a key for 1 hour
    }

    public StudentResponse get(int studentId){
        System.out.println("Inside get function of cache repository");
        if(studentId == 0){
            return null;
        }
        String key = STUDENT_KEY_PREFIX+studentId;
        return (StudentResponse) redisTemplate.opsForValue().get(key);
    }
}

