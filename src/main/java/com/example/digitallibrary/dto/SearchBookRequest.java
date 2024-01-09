package com.example.digitallibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class SearchBookRequest {

    @NotBlank
    private String searchKey;
    @NotBlank
    private String searchValue;
    @NotBlank
    private String operator;
    private boolean available;

    private static Set<String > allowedKeys = new HashSet<>();
    private  static Map<String, List<String>> allowedOperatorsMap = new HashMap<>();

    //constructor
    SearchBookRequest(){
        allowedKeys.addAll(Arrays.asList("name", "author_name", "genre", "pages", "id" ));//keys are allowed for searching
        //only there operators will be allowed for searching
        allowedOperatorsMap.put("name",Arrays.asList("=", "like"));
        allowedOperatorsMap.put("author_name", Arrays.asList("="));
        allowedOperatorsMap.put("pages", Arrays.asList("<", "<=", ">", ">=", "="));
        allowedOperatorsMap.put("genre", Arrays.asList("="));
        allowedOperatorsMap.put("id", Arrays.asList("="));
    }

    public boolean validate(){//check if keys are present or not

        List<String> validOperators = allowedOperatorsMap.get(this.searchKey);//all values for related search keys
        if(!validOperators.contains(this.operator)){//if the list doesn't contain the provided operator then return false;
            return false;
        }

        return true;//if everything is right then return true here
    }
}
