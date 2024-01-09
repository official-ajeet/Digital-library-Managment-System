package com.example.digitallibrary.dto;

import com.example.digitallibrary.models.Admin;
import com.example.digitallibrary.models.SecuredUser;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAdminRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String username;
    @NotBlank
    private String password;

    public Admin to (){
        return Admin.builder()
                .name(this.name)
                .securedUser(SecuredUser.builder()
                        .username(this.username)
                        .password(this.password)
                        .build())
                .build();
    }
}
