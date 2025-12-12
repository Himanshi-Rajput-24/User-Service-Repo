package com.example.user.service.service;

import com.example.user.service.common.ErrorType;
import com.example.user.service.entity.Address;
import com.example.user.service.entity.UserEntity;
import com.example.user.service.exceptions.CustomExceptions;
import com.example.user.service.repository.AddressRepository;
import com.example.user.service.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
public class AddressService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserService userService;


    public void getListOfAddress(){}


    public void saveAddress(Address address, String userId){
        try{
            UserEntity user = userRepository.findById(userId).orElse(null);
            if(address.getId() != null || address != null){
                user.getAddresses().add(address);
            }
            userService.updateUser(user, userId, null, false);
        }
        catch (Exception ex){
            throw new CustomExceptions(ErrorType.DATABASE_ERROR, ex);
        }
    }

    public void updateAddress(String userId, Long addressId){
        try{
            UserEntity user = userRepository.findById(userId).orElse(null);
            if(user !=null){
                userService.updateUser(user, userId, null, false);
            }
            else{
                log.info("User is Not Found");
                return ;
            }
        }
        catch (Exception ex){
            throw new CustomExceptions(ErrorType.DATABASE_ERROR, ex);
        }
    }


    public void deleteAddress(String userId, Long addressId){
        try{
          if(addressId != null && addressId != 0 && userId != null){
              UserEntity user = userRepository.findById(userId).orElse(null);
              userService.updateUser(user, userId, addressId, true);
          }

        }
        catch (Exception ex){
            throw new CustomExceptions(ErrorType.DATABASE_ERROR, ex);
        }
    }
}
