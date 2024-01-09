package com.example.digitallibrary.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Entity//this will be store in the db
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SecuredUser implements UserDetails {
    //attributes
    @Getter // - only need getter for id, else we already have getters
    @Id//primary key, hibernate need the id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private static final String AUTHORITIES_DELIMITER = "::";
    @Column(nullable = false,unique = true)
    private String username;
    private String password;
    private String authorities;

    @Getter
    @OneToOne(mappedBy = "securedUser")//for bidirectional relationship
    @JsonIgnoreProperties(value = {"securedUser","bookList"})
    private Student student;

    @Getter
    @OneToOne(mappedBy = "securedUser")//for bidirectional relationship
    @JsonIgnoreProperties(value = {"securedUser"})
    private Admin admin;

    //very important function - this function stores the authorities - there are two ways to do it
    //1. either you give multiple privileges to a single user, while authorization check for a particular privilege
    //2. (preferred approach) you give a role to a particular user and while authorizing check for multiple roles , - eg -this type of person can do this task or not
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String[] authoritiesList = this.authorities.split(AUTHORITIES_DELIMITER);//splitting authorities on the basis of delimiter
        return Arrays.stream(authoritiesList)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {//its like a getter
        return this.password;
    }

    @Override
    public String getUsername() {//like a getter
        return this.username;
    }

    //below functions are by default false, we can write some logic here
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
