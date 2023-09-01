package ru.urals.uralsapi;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

/**
 * The main class for Urals API
 */
@Slf4j
@EnableCaching
@SpringBootApplication
public class UralsApiApplication {

    public static void main(String[] args) {
        log.info("Entering application");
        SpringApplication.run(UralsApiApplication.class, args);

    }

}
