package ru.urals.uralsapi.viewmodel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.urals.uralsapi.controller.PriceController;
import ru.urals.uralsapi.model.Price;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 * Class responsible for managing the data for the price chart.
 * It interacts with the `PriceController` to fetch and update the prices.
 * It also maintains the start and end time for which the prices are to be fetched.
 */
@Component
public class PriceChartViewModel {
    private LocalDate startTime;
    private LocalDate endTime;
    private final PriceController priceController;
    private List<Price> prices = Collections.emptyList();

    @Autowired
    public PriceChartViewModel(PriceController priceController) {
        this.priceController = priceController;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
        refreshPrices();
    }

    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
        refreshPrices();
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public List<Price> getPrices() {
        return prices;
    }

    /**
     * Fetches the prices from the `PriceController` based on the start and end time.
     * If either the start or end time is not set, it sets the prices to an empty list.
     */
    private void refreshPrices() {
        if (startTime != null && endTime != null) {
            prices = priceController.getPricesByDateBetween(startTime, endTime);
        } else {
            prices = Collections.emptyList();
        }
    }

    /**
     * Adds a new price to the `PriceController` and returns whether the operation was successful.
     *
     * @param price The price to be added.
     * @return true if the operation was successful, false otherwise.
     */
    public boolean addPrice(Price price) {
        return priceController.create(price)
                .getStatusCode().is2xxSuccessful();
    }
}
