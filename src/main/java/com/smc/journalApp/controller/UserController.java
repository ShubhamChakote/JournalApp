package com.smc.journalApp.controller;

import com.smc.journalApp.entity.Users;
import com.smc.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;


    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody Users user){

        //This line will validate user is valid or not and also check is it null or not
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        Users userInDB = userService.findByusername(username);

        userInDB.setUsername(user.getUsername());
        userInDB.setPassword(user.getPassword());
        userService.saveNewUser(userInDB);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);


    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        userService.deleteByusername(authentication.getName());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }





}
