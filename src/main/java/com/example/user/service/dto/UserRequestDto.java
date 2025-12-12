package com.example.user.service.dto;

import lombok.Data;
import java.util.List;

@Data
public class UserRequestDto {
    private String userName;
    private String emailId;
    private String password;
    private List<String> roles;
    private String mobileNumber;
    private List<AddressDto> addresses;
}