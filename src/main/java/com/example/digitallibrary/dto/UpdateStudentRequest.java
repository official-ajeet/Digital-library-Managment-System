package com.example.digitallibrary.dto;

import com.example.digitallibrary.models.Student;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateStudentRequest {
    private String name;
    private String contact;
    private Date validity;

    Student to(){
        return Student.builder()
                .name(this.name)
                .contact(this.contact)
                .validity(this.validity)
                .build();
    }
}
