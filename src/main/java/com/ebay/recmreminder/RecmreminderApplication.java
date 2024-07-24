package com.ebay.recmreminder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class RecmreminderApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecmreminderApplication.class, args);
    }

}
