package com.example.digitallibrary.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    //to build the connection to the secured user
    @OneToOne
    @JoinColumn //join on the basis of username - foreign key -new- join with the pk only
    @JsonIgnoreProperties(value = {"admin"})
    private SecuredUser securedUser;
}
