package com.bharatbazaar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class})
public class BharatBazaarApplication {
    public static void main(String[] args) {
        SpringApplication.run(BharatBazaarApplication.class, args);
    }
}
