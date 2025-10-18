package com.smc.journalApp.controller;

import com.smc.journalApp.api.response.WeatherResponse;
import com.smc.journalApp.entity.User;
import com.smc.journalApp.scheduler.UserScheduler;
import com.smc.journalApp.service.EmailService;
import com.smc.journalApp.service.UserService;
import com.smc.journalApp.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Tag(name = "User APIs")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
        private WeatherService weatherService;

    @Autowired
    private UserScheduler userScheduler;

    @Operation(summary = "Api for updating the user information")
    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){

        //This line will validate user is valid or not and also check is it null or not
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        User userInDB = userService.findByusername(username);

        userInDB.setUsername(user.getUsername());
        userInDB.setPassword(user.getPassword());
        userService.saveNewUser(userInDB);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);


    }

    @Operation(summary = "Api for deleting the user")
    @DeleteMapping
    public ResponseEntity<?> deleteUser(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        userService.deleteByusername(authentication.getName());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Api for fetching info about current weather")
    @GetMapping
    public ResponseEntity<?> greetings(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        WeatherResponse weatherResponse = weatherService.getWeather ("Pune");
        String greeting = "";
        if (weatherResponse != null) {
            greeting = " Weather feels like " + weatherResponse.getCurrent().getFeelslike();
        }

        return new ResponseEntity<>("Hi "+authentication.getName() + greeting,HttpStatus.OK);
    }

    @Operation(summary = "Api for sending email of sentiment analysis")
    @PostMapping("/sendMail")
    public void sendEmail(){

        userScheduler.fetchUsersAndSendSAMail();
        System.out.println("mail sent");

    }



}
