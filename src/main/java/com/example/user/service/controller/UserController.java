package com.example.user.service.controller;

import com.example.user.service.common.ErrorType;
import com.example.user.service.entity.UserEntity;
import com.example.user.service.exceptions.CustomExceptions;
import com.example.user.service.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Tag(name = "User Controller", description = "This controller is used to update and delete the user.")

public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/getUserById/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable String id) {
        return userService.getUserById(id)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public List<UserEntity> getUserList() {
        return userService.getUserList();
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<String> updateUser(@RequestBody UserEntity user, @PathVariable String id) {
        try {
            userService.updateUser(user, id, null, false);
            return ResponseEntity.ok("User updated successfully");
        } catch (CustomExceptions ex) {
            return new ResponseEntity<>(ex.getMessage(), ex.getErrorType().getHttpStatus());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unexpected error: " + ex.getMessage());
        }
    }


    @DeleteMapping("/deleteUser")
    public ResponseEntity<String> deleteUser(@RequestParam String id) {
        try {
            userService.deleteUser(id);
            return new ResponseEntity<>("User has been deleted", HttpStatus.NO_CONTENT);
        } catch (CustomExceptions ex) {
            return new ResponseEntity<>(ErrorType.DATABASE_ERROR.getMessage(), ErrorType.DATABASE_ERROR.getHttpStatus());
        }
    }
}
