package com.rishi.PokePedia;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching
public class PokePediaApplication {

    public static void main(String[] args) {

        SpringApplication.run(PokePediaApplication.class, args);

        System.out.println("API is up");
    }
}
