package com.example.digitallibrary.repository;

import com.example.digitallibrary.models.SecuredUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<SecuredUser,Integer> {
    SecuredUser findByUsername(String user);
}
