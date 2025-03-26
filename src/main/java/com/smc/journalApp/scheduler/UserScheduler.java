package com.smc.journalApp.scheduler;

import com.smc.journalApp.entity.JournalEntry;
import com.smc.journalApp.entity.User;
import com.smc.journalApp.enums.Sentiment;
import com.smc.journalApp.repository.UserRepositoryImpl;
import com.smc.journalApp.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    private UserRepositoryImpl userRepository;



    @Autowired
    private EmailService emailService;

    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUsersAndSendSAMail(){

        List<User> userForSA = userRepository.getUserForSA();

        for (User user : userForSA) {

            List<JournalEntry> journalEntries = user.getJournalEntries();

            //below we have taken sentiment of journal entries from last 7 days
            List<Sentiment> sentiments = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x->x.getSentiment()).collect(Collectors.toList());

            Map<Sentiment,Integer> setimentCount = new HashMap<>();

            for (Sentiment sentiment : sentiments) {

                if (sentiment != null) {
                    setimentCount.put(sentiment, setimentCount.getOrDefault(sentiment, 0) + 1);
                }
            }

                Sentiment mostFrequentSentiment = null;
                int maxCount = 0;
                for (Map.Entry<Sentiment,Integer> entry :setimentCount.entrySet()){

                    if(entry.getValue() > maxCount){
                        maxCount = entry.getValue();
                        mostFrequentSentiment = entry.getKey();
                    }

                }

                if (mostFrequentSentiment != null){
                    emailService.sendMail(user.getEmail(),"Sentiment Analysis of last 7 days",mostFrequentSentiment.toString());
                }









        }


    }

}
