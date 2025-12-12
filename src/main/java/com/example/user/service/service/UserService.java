package com.example.user.service.service;

import com.example.user.service.common.ErrorType;
import com.example.user.service.dto.UserRequestDto;
import com.example.user.service.dto.UserResponseDto;
import com.example.user.service.entity.Address;
import com.example.user.service.entity.UserEntity;
import com.example.user.service.exceptions.CustomExceptions;
import com.example.user.service.mapper.UserMapper;
import com.example.user.service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Optional<UserEntity> getUserById(String id) {
        return userRepository.findById(id);
    }

    public List<UserResponseDto> getUserList() {
        List<UserEntity> userList = userRepository.findAll();
        List<UserResponseDto> userResponseDtos = new ArrayList<>();
        for(UserEntity userEntity : userList){
            UserResponseDto userResponseDto = userMapper.toDto(userEntity);
            userResponseDtos.add(userResponseDto);
        }
        return userResponseDtos;
    }

    public UserEntity saveUser(UserRequestDto userDto) {
        if (userDto.getEmailId() == null) {
            throw new CustomExceptions(ErrorType.MISSING_REQUIRED_FIELDS);
        }
        try {
            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
            UserEntity userEntity = new UserEntity();
            userMapper.fromDto(userDto, userEntity);
            return userRepository.save(userEntity);
        } catch (CustomExceptions ex) {
            throw new CustomExceptions(ErrorType.DATABASE_ERROR);
        }
    }

    public void updateUser(UserEntity user, String id, Long addressId, boolean isDeletedAddress) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();


        if (id == null || id.trim().isEmpty()) {
            throw new CustomExceptions(ErrorType.MISSING_REQUIRED_FIELDS);
        }

        UserEntity existingUser = userRepository.findByUserName(userName)
                .orElseThrow(() -> new CustomExceptions(ErrorType.USER_NOT_FOUND));

        // Update user fields conditionally
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        if (user.getEmailId() != null) {
            existingUser.setEmailId(user.getEmailId());
        }
        if (user.getUserName() != null) {
            existingUser.setUserName(user.getUserName());
        }

        updateAddress(existingUser, user, addressId, isDeletedAddress);

        existingUser.setUpdateAt(LocalDateTime.now());
        userRepository.save(existingUser);
    }


    public void deleteUser(String id) {
        if (id == null) {
            throw new CustomExceptions(ErrorType.MISSING_REQUIRED_FIELDS);
        }
        Optional<UserEntity> user = userRepository.findById(id);
        try {
            if (user.isPresent()) {
                userRepository.deleteById(id);
            } else {
                throw new CustomExceptions(ErrorType.USER_NOT_FOUND);
            }
        } catch (CustomExceptions ex) {
            throw new CustomExceptions(ErrorType.DATABASE_ERROR, ex);

        }
    }

    public void updateAddress(UserEntity existingUser, UserEntity user, Long addressId, boolean isDeletedAddress) {
        if(addressId != null && isDeletedAddress){
            List<Address> addresses = existingUser.getAddresses();
            addresses.removeIf(address -> address.getId().equals(addressId));
            existingUser.setAddresses(addresses);
        }

        else {
            updateSavedAddress(existingUser, user, existingUser.getAddresses(), user.getAddresses());
        }


    }

    public void updateSavedAddress(UserEntity existingUser, UserEntity user, List<Address> savedAddress, List<Address> newAddress){


            if (newAddress == null || newAddress.isEmpty()) {
                return;
            }

            if (savedAddress == null) {
                savedAddress = new ArrayList<>();
            }

            for (Address newAddr : newAddress) {
                if (newAddr.getId() != null) {
                    // New address, add to list
                    newAddr.setUser(existingUser);
                    existingUser.getAddresses().add(newAddr);
                } else {
                    boolean updated = false;
                    for (Address oldAddr : existingUser.getAddresses()) {
                        if (newAddr.getId().equals(oldAddr.getId())) {
                            oldAddr.setStreet(newAddr.getStreet());
                            oldAddr.setCity(newAddr.getCity());
                            oldAddr.setState(newAddr.getState());
                            oldAddr.setZipCode(newAddr.getZipCode());
                            oldAddr.setCountry(newAddr.getCountry());
                            oldAddr.setUser(existingUser);
                            updated = true;
                            break;
                        }
                    }
                    // If address with ID not found, treat it as a new address (optional)
                    if (!updated) {
                        newAddr.setUser(existingUser);
                        existingUser.getAddresses().add(newAddr);
                    }
                }
            }


    }

}
