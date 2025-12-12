package com.example.user.service.mapper;

import com.example.user.service.dto.AddressDto;
import com.example.user.service.entity.Address;
import com.example.user.service.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public AddressDto toDto(Address address){
        AddressDto addressDto = new AddressDto();
        addressDto.setCity(address.getCity());
        addressDto.setStreet(address.getStreet());
        addressDto.setState(address.getState());
        addressDto.setCountry(address.getCountry());
        addressDto.setZipCode(address.getZipCode());
        return addressDto;
    }

    public Address fromDto(AddressDto dtoAddr, UserEntity user){
        Address address = new Address();
        address.setStreet(dtoAddr.getStreet());
        address.setCity(dtoAddr.getCity());
        address.setState(dtoAddr.getState());
        address.setZipCode(dtoAddr.getZipCode());
        address.setCountry(dtoAddr.getCountry());
        address.setUser(user); // REQUIRED
        return address;
    }
}