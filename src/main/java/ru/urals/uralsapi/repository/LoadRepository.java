package ru.urals.uralsapi.repository;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class for preloading data into the prices repository
 *
 * <p>The class provides a `CommandLineRunner` method that is executed when the application starts.
 * The method loads data from a file with given date format and uploads it into the repository.
 * Path to file and date format are defined in application.properties file .</p>
 */
@Slf4j
@Configuration
public class LoadRepository {

    @Value("${dateFormat}")
    private String dateFormat;
    @Value("${fileName}")
    private String fileName;


    /**
     * Preloads data into the price repository
     * @param repository Price repository
     */
    @Bean
    CommandLineRunner initRepo(PriceRepository repository) {
        return args -> {
            File flag = new File("src/main/resources/db_initialized");
            if(!flag.exists()) {
                log.info("Preloading data from CSV");
                PriceFromCsvLoader.LoadPricesFromCsv(fileName, repository, dateFormat);

                FileWriter Writer = new FileWriter("src/main/resources/db_initialized");
                Writer.write("Prices were successfully loaded!");
                Writer.close();
            }
        };
    }
}
