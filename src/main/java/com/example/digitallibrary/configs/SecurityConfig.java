package com.example.digitallibrary.configs;

import com.example.digitallibrary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    UserService userService;

    //authorization
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws  Exception{

        http.httpBasic().and().csrf().disable()//disable it only for testing purpose , not for real programs
                .authorizeHttpRequests((authz)-> authz
                        .antMatchers("/student/details/**").hasAuthority("get-student-profile")//student
                        .antMatchers(HttpMethod.GET,"/student/**").hasAuthority("get-student-details")//only get details for admin
                        .antMatchers(HttpMethod.POST,"/student/**").permitAll()//if any api having post method then starting with student - ant matcher for create student of signup api
                        .antMatchers("/student/**").hasAuthority("update-student-account")//else update, delete for that student - access to student
//for book
                        .antMatchers(HttpMethod.GET,"/book/**").hasAuthority("get-book-details")//"student","admin"
                        .antMatchers("/book/**").hasAuthority("update-book")//"admin"= any other api authorize to admin only - create , delete
//for transaction
                        .antMatchers("/transaction/**").hasAuthority("book-transaction")//"student"
                        .antMatchers(HttpMethod.POST,"/admin/**").hasAuthority("add-admin"))
                        .formLogin();

        //authentication
        http.authenticationProvider(getDaoAuthProvider());

        return http.build();
    }

    //authentication impl
    private DaoAuthenticationProvider getDaoAuthProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
