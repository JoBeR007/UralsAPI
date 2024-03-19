package ru.urals.uralsapi;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import ru.urals.uralsapi.controller.PriceController;
import ru.urals.uralsapi.view.PriceChartView;
import ru.urals.uralsapi.view.PriceView;
import ru.urals.uralsapi.viewmodel.PriceChartViewModel;

import javax.swing.*;

/**
 * The main class for Urals API
 */
@Slf4j
@EnableCaching
@SpringBootApplication
public class UralsApiApplication {

    public static void main(String[] args) {
        log.info("Entering application");

        ConfigurableApplicationContext context = SpringApplication.run(UralsApiApplication.class, args);

        // Get the PriceController bean from the Spring application context
        PriceController priceController = context.getBean(PriceController.class);

        PriceView priceView = new PriceView();
        priceView.configure(priceController);
    }

}
