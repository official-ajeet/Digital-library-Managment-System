package com.example.digitallibrary.repository;

import com.example.digitallibrary.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Integer> {

}
