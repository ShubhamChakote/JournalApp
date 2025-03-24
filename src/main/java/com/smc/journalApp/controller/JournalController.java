package com.smc.journalApp.controller;

import com.smc.journalApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/_journal")
public class JournalController {

    Map<ObjectId,JournalEntry> journalEntries = new HashMap<>();

    @GetMapping
    public List<JournalEntry> getAllEntires(){
        return new ArrayList<>(journalEntries.values());
    }

    @PostMapping
    public boolean createEntry(@RequestBody JournalEntry myEntry){
        journalEntries.put(myEntry.getId(),myEntry);
        return true;
    }

    @GetMapping("id/{id}")
    public JournalEntry getJournalEntryById(@PathVariable Long id){
        return journalEntries.get(id);
    }

    @DeleteMapping("id/{id}")
    public String deleteJournalEntryById(@PathVariable Long id){
        journalEntries.remove(id);
        return "Journal Entry"+ id+" was removed successfully";
    }

    @PutMapping("id/{id}")
    public JournalEntry updateJournalEntryById(@PathVariable ObjectId id , @RequestBody JournalEntry myEntry){
        return journalEntries.put(id,myEntry);
    }



}
