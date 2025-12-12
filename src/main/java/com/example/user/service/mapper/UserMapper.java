package com.example.user.service.mapper;

import com.example.user.service.dto.AddressDto;
import com.example.user.service.dto.UserRequestDto;
import com.example.user.service.dto.UserResponseDto;
import com.example.user.service.entity.Address;
import com.example.user.service.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {

    @Autowired
    private AddressMapper addressMapper;

    public UserResponseDto toDto(UserEntity entity) {
        UserResponseDto userDto = new UserResponseDto();
        userDto.setId(entity.getId());
        userDto.setUserName(entity.getUserName());
        userDto.setEmailId(entity.getEmailId());
        userDto.setMobileNumber(entity.getMobileNumber());
        List<AddressDto> addressesDto = new ArrayList<>();
        for(Address address : entity.getAddresses()){
            AddressDto addressDto = addressMapper.toDto(address);
            addressesDto.add(addressDto);
        }
        userDto.setAddress(addressesDto);
        return userDto;
    }

    public void fromDto(UserRequestDto userDto, UserEntity user){
        user.setPassword(userDto.getPassword());
        user.setUserName(userDto.getUserName());
        user.setEmailId(userDto.getEmailId());
        user.setMobileNumber(userDto.getMobileNumber());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdateAt(LocalDateTime.now());
        user.setRoles(userDto.getRoles());
        List<Address> addressList = new ArrayList<>();
        for (AddressDto dtoAddr : userDto.getAddresses()) {
            Address address = addressMapper.fromDto(dtoAddr, user);
            addressList.add(address);
        }
        user.setAddresses(addressList);
    }
}