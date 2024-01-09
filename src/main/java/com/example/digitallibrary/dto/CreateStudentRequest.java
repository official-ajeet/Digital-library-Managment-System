package com.example.digitallibrary.dto;

import com.example.digitallibrary.models.SecuredUser;
import com.example.digitallibrary.models.Student;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateStudentRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String contact;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public Student to(){//convert request to Student
        return Student.builder()
                .name(this.name)
                .contact(this.contact)
                .securedUser(
                        SecuredUser.builder()
                                .username(username)
                                .password(password)
                                .build()
                )
                .validity(new Date(System.currentTimeMillis() + 31536000000l))//1 year from now
                .build();
    }
}
