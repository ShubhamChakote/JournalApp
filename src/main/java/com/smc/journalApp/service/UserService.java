package com.smc.journalApp.service;

import com.smc.journalApp.entity.User;
import com.smc.journalApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder pwdEncoder = new BCryptPasswordEncoder();


    public void saveUser(User user){

        try {
            userRepository.save(user);
        } catch (Exception e) {
            log.error("Exception ", e);
        }


    }

    public void saveNewUser(User user){

        try {
            user.setPassword(pwdEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
        } catch (Exception e) {
            log.error("Exception ", e);
        }


    }

    public void saveAdmin(User user){
        user.setPassword(pwdEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        userRepository.save(user);
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public Optional<User> getJournalById(ObjectId id){
        //Optional is a class which might contains data or not
        return userRepository.findById(id);
    }

    public void deletById(ObjectId id){
        userRepository.deleteById(id);
    }

    public User findByusername(String username){
        return userRepository.findByusername(username);
    }

    public void deleteByusername(String username){
        userRepository.deleteByusername(username);
    }

}
