package com.smc.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService {

    @Autowired
//    // JavaMailSender is interface use to send mails
    private JavaMailSender javaMailSender;


    public void sendMail(String to, String subject, String body){

        try {
            /*
            SimpleMailMessage is a class in  java used
            to specify or set the content of mail
             */
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(to);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(body);
            javaMailSender.send(simpleMailMessage);

        }
        catch (Exception e){
            log.error("Error occurred while sending mail",e);
        }

    }

}
