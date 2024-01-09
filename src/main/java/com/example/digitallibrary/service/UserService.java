package com.example.digitallibrary.service;

import com.example.digitallibrary.models.SecuredUser;
import com.example.digitallibrary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       return this.userRepository.findByUsername(username);
    }

    public SecuredUser save(SecuredUser securedUser) {
        return this.userRepository.save(securedUser);
    }

    public void delete(int id){
        this.userRepository.deleteById(id);
    }
}
