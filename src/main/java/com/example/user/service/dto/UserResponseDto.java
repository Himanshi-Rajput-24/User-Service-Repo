package com.example.user.service.dto;

import lombok.Data;
import java.util.List;

@Data
public class UserResponseDto {
    private String id;
    private String userName;
    private String emailId;
    private String mobileNumber;
    private List<AddressDto> address;
}