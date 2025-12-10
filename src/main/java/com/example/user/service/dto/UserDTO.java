package com.example.user.service.dto;

import lombok.Data;
@Data
public class UserDTO {
    private String id;
    private String emailId;

    public UserDTO(){}
    public UserDTO(String emailId){
        this.emailId = emailId;
    }
}
