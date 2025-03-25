package com.smc.journalApp.service;

import com.smc.journalApp.entity.User;
import com.smc.journalApp.repository.JournalEntryRepository;
import com.smc.journalApp.entity.JournalEntry;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveJournals(JournalEntry journalEntry, String username){

        try {
            User user = userService.findByusername(username);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry savedJournals = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(savedJournals);
            userService.saveUser(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    public void saveJournals(JournalEntry journalEntry){

        try {
            journalEntry.setDate(LocalDateTime.now());
            journalEntryRepository.save(journalEntry);

        } catch (Exception e) {
            log.error("Exception ", e);
        }


    }



    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getJournalById(ObjectId id){
        //Optional is a class which might contains data or not
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public boolean deletById(ObjectId id, String username){

        boolean removed = false;
        try {
            User user = userService.findByusername(username);
            removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));

            if(removed){
                userService.saveUser(user);
                journalEntryRepository.deleteById(id);
            }

        } catch (Exception e) {
            throw new RuntimeException("An error occurred while deleting journal entry",e);
        }

        return removed;
    }


}
