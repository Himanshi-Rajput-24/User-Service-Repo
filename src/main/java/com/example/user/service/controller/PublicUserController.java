package com.example.user.service.controller;

import com.example.user.service.entity.UserEntity;
import com.example.user.service.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/public")
@Tag(name = "Public Controller", description = "This controller is used to create user.")
public class PublicUserController {
    private UserService userService;

    public PublicUserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signUp")
    public ResponseEntity<String> createUser(@RequestBody UserEntity userEntity) {
        try {
            userService.saveUser(userEntity);
            return new ResponseEntity<>("User saved in db successfully", HttpStatus.CREATED);
        } catch (Exception ex) {
            log.error("Please Enter the Unique Username and EmailId"+ex);
            return new ResponseEntity<>("Error while creating new user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
