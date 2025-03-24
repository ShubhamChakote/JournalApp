package com.smc.journalApp.controller;


import com.smc.journalApp.entity.Users;
import com.smc.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers(){
        List<Users> allUsers = userService.getAll();

        if (allUsers != null && !allUsers.isEmpty()){
            return new ResponseEntity<>(allUsers, HttpStatus.OK);
        }

        return new ResponseEntity<>(allUsers, HttpStatus.NOT_FOUND);

    }

    @PostMapping("/add-admin")
    public ResponseEntity<?> createNewAdmin(@RequestBody Users user){
        userService.saveAdmin(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
