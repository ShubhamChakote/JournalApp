package com.smc.journalApp.controller;

import com.smc.journalApp.entity.JournalEntry;
import com.smc.journalApp.entity.User;
import com.smc.journalApp.service.JournalEntryService;
import com.smc.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;


    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesForUser(){


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        User user = userService.findByusername(username);

        List<JournalEntry> journalEntries = user.getJournalEntries();

        if(journalEntries != null && !journalEntries.isEmpty()){
            return new ResponseEntity<>(journalEntries,HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry){

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            journalEntryService.saveJournals(myEntry,username);
            return new ResponseEntity<JournalEntry>(myEntry,HttpStatus.CREATED);

        }
        catch (Exception e) {
            return new ResponseEntity<JournalEntry>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("id/{id}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId id){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByusername(username);

        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x->x.getId().equals(id)).collect(Collectors.toList());

        if(!collect.isEmpty()){

            Optional<JournalEntry> journalEntry = journalEntryService.getJournalById(id);

            if (journalEntry.isPresent()){
                return new ResponseEntity<JournalEntry>(journalEntry.get(), HttpStatus.OK);
            }

        }



         return new ResponseEntity<JournalEntry>(HttpStatus.FORBIDDEN);

    }

    @DeleteMapping("id/{id}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        boolean removed = journalEntryService.deletById(id,username);
        if (removed) return new ResponseEntity<JournalEntry>(HttpStatus.NO_CONTENT);
        else return new ResponseEntity<JournalEntry>(HttpStatus.NOT_FOUND);

    }

    @PutMapping("id/{id}")
    public ResponseEntity<?> updateJournalEntryById(
            @PathVariable ObjectId id ,
            @RequestBody JournalEntry updatedEntry
            ){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByusername(username);

        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x->x.getId().equals(id)).collect(Collectors.toList());

        if(!collect.isEmpty()){

            JournalEntry oldEntry = journalEntryService.getJournalById(id).get();

            oldEntry.setTitle(updatedEntry.getTitle() != null && !updatedEntry.getTitle().equals("") ? updatedEntry.getTitle() : oldEntry.getTitle());
            oldEntry.setContent(updatedEntry.getContent()!=null && !updatedEntry.getContent().equals("") ? updatedEntry.getContent():oldEntry.getContent());
            journalEntryService.saveJournals(oldEntry);
            return new ResponseEntity<>(oldEntry,HttpStatus.OK);

        }



        return new ResponseEntity<>(HttpStatus.NOT_FOUND);


    }



}
