package com.example.user.service.controller;

import com.example.user.service.entity.Address;
import com.example.user.service.entity.UserEntity;
import com.example.user.service.repository.AddressRepository;
import com.example.user.service.service.AddressService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Generated;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("/address")
@RestController
@Slf4j
@Tag(name= "Address Controller", description = "This Controller is used to store the address of users" )
public class AddressController {
    private final AddressService addressService;
    private final AddressRepository addressRepository;

    public AddressController(AddressService addressService, AddressRepository addressRepository){
        this.addressService = addressService;
        this.addressRepository = addressRepository;
    }

    @GetMapping("/getListOfUserAddresses/{userId}")
    public List<Address> getListOfUserAddresses(@PathVariable String userId){
        return addressRepository.findByUserId(userId);
    }

    @PostMapping("/saveAddress/{user_id}")
    public ResponseEntity<String> saveAddress(@RequestBody Address address, @PathVariable String user_id){
        try{
            log.info("Enter in------->  "+this.getClass().getSimpleName());
            addressService.saveAddress(address, user_id);
            log.info("Address have been save in Db");
            return new ResponseEntity<>("Successfully saved", HttpStatus.OK);
        }
        catch (Exception ex){
            log.error("Facing issue while saving address in db"+ex.getMessage());
            return new ResponseEntity<>("Failed to save", HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PutMapping("/updateUser")
    public ResponseEntity<String> updateAddress(@RequestParam String userId, @RequestParam Long addressId){
        try{
            log.info("Enter in------->  "+this.getClass().getSimpleName());
            addressService.updateAddress(userId, addressId);
            log.info("Address have been save in Db");
            return new ResponseEntity<>("Successfully saved", HttpStatus.OK);
        }
        catch (Exception ex){
            log.error("Facing issue while saving address in db"+ex.getMessage());
            return new ResponseEntity<>("Failed to save", HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @DeleteMapping("/deleteAddress")
    public ResponseEntity<String> deleteAddress(@RequestParam String userId, @RequestParam Long addressId){
        log.info("Enter in------->  "+this.getClass().getSimpleName());
        try{
            addressService.deleteAddress(userId, addressId);
            log.info("Address has been deleted from the db");
            return new ResponseEntity<>("Successfully Deleted", HttpStatus.OK);

        }
        catch (Exception ex){
            log.error("Facing issue while saving address in db"+ex.getMessage());
            return new ResponseEntity<>("Failed to save", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
