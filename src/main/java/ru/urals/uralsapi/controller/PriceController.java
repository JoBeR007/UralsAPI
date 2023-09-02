package ru.urals.uralsapi.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.urals.uralsapi.model.MinMaxRange;
import ru.urals.uralsapi.model.Price;
import ru.urals.uralsapi.repository.PriceRepository;
import ru.urals.uralsapi.util.DateParser;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

/**
 * REST Controller for UralsAPI
 */
@Slf4j
@RestController
@RequestMapping("/urals-api")
@CacheConfig(cacheNames = {"urals-api"})
public class PriceController {

    private final PriceRepository repository;

    @Autowired
    PriceController(PriceRepository repository) {
        this.repository = repository;
    }

    /**
     * Adds new Price to repository from given POST request with json
     *
     * @param newPrice Price constructed from provided json file
     * @return Response entity with instance of created Price
     * or HttpStatus.CONFLICT for already present Price
     */
    @Cacheable(key = "#newPrice")
    @PostMapping
    public ResponseEntity<Price> create(@RequestBody Price newPrice) {
        log.info("Creating new Price");
        if (repository.getPriceByDate(newPrice.getDate()).isPresent()) {
            log.info("Price with date {} already exists", newPrice.getDate());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else return new ResponseEntity<>(repository.save(newPrice), HttpStatus.CREATED);
    }

    /**
     * Deletes Price from repository by given id
     *
     * @param id price id
     * @return Response entity with HttpStatus.OK or HttpStatus.BAD_REQUEST
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Long id) {
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Gets Price for given id
     *
     * @param id price id
     * @return Response entity with price if it is present
     * or HttpStatus.BAD_REQUEST if it is not present
     */
    @Cacheable(key = "#id")
    @GetMapping("/by-id/{id}")
    public ResponseEntity<Price> findById(@PathVariable Long id) {
        log.info("Getting Price by id");
        Optional<Price> result = repository.findById(id);
        if (result.isPresent()) {
            return new ResponseEntity<>(result.get(), HttpStatus.OK);
        } else {
            log.info("No price for id: {}", id);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Gets price value for given date
     *
     * @param date price date
     * @return Response entity with price value if it is present
     * or HttpStatus.BAD_REQUEST if it is not present
     */
    @Cacheable(key = "#date")
    @GetMapping("/by-date")
    public ResponseEntity<Double> findByDate(@RequestParam(name = "date") String date) {
        log.info("Getting Price by date: {}", date);
        Optional<Price> result = repository.getPriceByDate(DateParser.parseDateFromString(date));
        if (result.isPresent()) {
            return new ResponseEntity<>(result.get().getPrice(), HttpStatus.OK);
        } else {
            log.info("No Price for date: {}", date);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Gets full information about price for given date
     *
     * @param date price date
     * @return Response entity with Price if it is present or HttpStatus.BAD_REQUEST if it is not present
     */
    @Cacheable(key = "{methodName, #date}")
    @GetMapping("/by-date-full")
    public ResponseEntity<Price> findByDateFullPrice(@RequestParam(name = "date") String date) {
        log.info("Getting Full Price by date: {}", date);
        Optional<Price> result = repository.getPriceByDate(DateParser.parseDateFromString(date));
        if (result.isPresent()) {
            return new ResponseEntity<>(result.get(), HttpStatus.OK);
        } else {
            log.info("No Price for date: {}", date);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Gets an average price value for given range of dates
     *
     * @param from start of date range
     * @param to   end of date range
     * @return Response entity with price value if both prices are present
     * or HttpStatus.BAD_REQUEST even if one of prices is not present
     * or HttpStatus.NOT_FOUND for empty repository
     */
    @Cacheable(key = "{methodName, #from, #to}")
    @GetMapping("/average")
    public ResponseEntity<Double> averageBetween(@RequestParam String from,
                                                 @RequestParam String to) {
        log.info("Getting average for dates: {} to {}", from, to);
        List<Price> prices = repository.getPriceByDateBetween
                (DateParser.parseDateFromString(from), DateParser.parseDateFromString(to));
        OptionalDouble avg = prices.stream().mapToDouble(Price::getPrice).average();
        if (avg.isPresent()) {
            return new ResponseEntity<>(avg.getAsDouble(), HttpStatus.OK);
        } else if (repository.count() != 0) {
            log.info("No average for given dates: {} and {}", from, to);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            log.warn("Repository is empty");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Gets minimum and maximum price values for given range of dates
     *
     * @param from start of date range
     * @param to   end of date range
     * @return Response entity with custom fields for minimum and maximum price values and their difference,
     * or HttpStatus.BAD_REQUEST when at least one of prices is not present
     * or HttpStatus.NOT_FOUND for empty repository
     */
    @Cacheable(key = "{methodName, #from, #to}")
    @GetMapping("/min-max")
    public ResponseEntity<MinMaxRange> minAndMaxBetween(@RequestParam String from, @RequestParam String to) {
        log.info("Getting minimum and maximum Prices between: {} and {}", from, to);
        List<Price> prices = repository.getPriceByDateBetween
                (DateParser.parseDateFromString(from), DateParser.parseDateFromString(to));
        OptionalDouble min = prices.stream().mapToDouble(Price::getPrice).min();
        OptionalDouble max = prices.stream().mapToDouble(Price::getPrice).max();

        if (min.isPresent() && max.isPresent()) {
            return new ResponseEntity<>(new MinMaxRange(min.getAsDouble(), max.getAsDouble(),
                    max.getAsDouble() - min.getAsDouble()), HttpStatus.OK);
        } else if (repository.count() != 0) {
            log.info("No Price(s) for given dates: {} and {}", from, to);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            log.warn("Repository is empty");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Gets statistics in form of MinMaxRange entity with fields for information
     *
     * @return Response entity with MinMaxRange entity that contains fields for
     * minimum and maximum prices, corresponding dates, difference between minimum and maximum,
     * overall average price, and total amount of entries in repository
     * or HttpStatus.NOT_FOUND for empty repository
     */
    @Cacheable
    @GetMapping("/stats")
    public ResponseEntity<MinMaxRange> statistics() {
        log.info("Getting statistics for repository");
        Optional<Price> minPrice = repository.findMinPrice();
        Optional<Price> maxPrice = repository.findMaxPrice();
        Optional<Double> avgPrice = repository.averagePrice();

        if (minPrice.isPresent() && maxPrice.isPresent() && avgPrice.isPresent()) {
            return new ResponseEntity<>(new MinMaxRange(minPrice.get().getPrice(),
                    minPrice.get().getDate(), maxPrice.get().getPrice(),
                    maxPrice.get().getDate(), repository.count(),
                    maxPrice.get().getPrice() - minPrice.get().getPrice(),
                    avgPrice.get()), HttpStatus.OK);
        } else if (repository.count() == 0) {
            log.warn("Repository is empty");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            log.warn("Repository is missing values");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
