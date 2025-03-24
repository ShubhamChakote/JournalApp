package com.smc.journalApp.service;

import com.smc.journalApp.entity.Users;
import com.smc.journalApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder pwdEncoder = new BCryptPasswordEncoder();


    public void saveUser(Users user){

        try {
            userRepository.save(user);
        } catch (Exception e) {
            log.error("Exception ", e);
        }


    }

    public void saveNewUser(Users user){

        try {
            user.setPassword(pwdEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
        } catch (Exception e) {
            log.error("Exception ", e);
        }


    }

    public void saveAdmin(Users user){
        user.setPassword(pwdEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        userRepository.save(user);
    }

    public List<Users> getAll(){
        return userRepository.findAll();
    }

    public Optional<Users> getJournalById(ObjectId id){
        //Optional is a class which might contains data or not
        return userRepository.findById(id);
    }

    public void deletById(ObjectId id){
        userRepository.deleteById(id);
    }

    public Users findByusername(String username){
        return userRepository.findByusername(username);
    }

    public void deleteByusername(String username){
        userRepository.deleteByusername(username);
    }

}
