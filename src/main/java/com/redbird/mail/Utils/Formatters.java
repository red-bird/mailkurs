package com.redbird.mail.Utils;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class Formatters {
    @Bean
    public DateTimeFormatter formatter() {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");
    }
}
