package com.netcracker.mycosts.activities;

import com.netcracker.mycosts.services.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MailActivity {
    @Autowired
    private MailSender mailSender;

    /*@Scheduled(fixedRate = 60000)
    public void sendEmail(){
        String message = String.format("Hello");
        mailSender.send("yankova.nastya@yandex.ru", "Costs by april", message);
    }*/
}
