package ru.urals.uralsapi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import ru.urals.uralsapi.controller.PriceController;
import ru.urals.uralsapi.view.PriceView;

/**
 * The main class for Urals API
 */
@Slf4j
@EnableCaching
@SpringBootApplication
public class UralsApiApplication {

    public static void main(String[] args) {
        log.info("Entering application");
        System.setProperty("java.awt.headless", "false");
        ConfigurableApplicationContext context = SpringApplication.run(UralsApiApplication.class, args);

        // Get the PriceController bean from the Spring application context
        PriceController priceController = context.getBean(PriceController.class);

        PriceView priceView = new PriceView();
        priceView.configure(priceController);
    }

}
