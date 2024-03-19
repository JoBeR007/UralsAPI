package ru.urals.uralsapi.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.urals.uralsapi.model.Price;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository for storing and retrieving Prices.
 *
 * <p>The repository provides methods for adding and deleting prices.
 * It also provides methods for retrieving prices by ID, Date and period of Dates and
 * for finding the minimum and maximum prices.</p>
 */
public interface PriceRepository extends CrudRepository<Price, Long> {

    /**
     * Gets price for corresponding date.
     *
     * @param date date to use for getting
     * @return Optional price that contains price for given date if it exists,
     * or an empty Optional if there is no price for given date
     */
    Optional<Price> getPriceByDate(LocalDate date);

    /**
     * Deletes price for corresponding date.
     *
     * @param date date to use for deleting
     * @return true if Price was present and was successfully deleted
     * and false if no Price object with the specified date was found in the repository, so no deletion occurred
     */
    boolean deleteByDate(LocalDate date);

    /**
     * Gets the prices in the given range of dates.
     *
     * @param dateBefore start of date range
     * @param dateAfter  end of date range
     * @return list of prices that fall within given range of dates
     */
    List<Price> getPriceByDateBetween(LocalDate dateBefore, LocalDate dateAfter);

    /**
     * Gets the minimum price in the database.
     *
     * <p>The method executes a custom query to find the minimum price in the database.</p>
     *
     * @return an Optional price that contains the minimum price if it exists,
     * or an empty Optional if there are no prices in the database
     */
    @Query("SELECT p FROM Price p WHERE p.price = (SELECT MIN(price) FROM Price)")
    Optional<Price> findMinPrice();

    /**
     * Gets the maximum price in the database.
     *
     * <p>The method executes a custom query to find the maximum price in the database.</p>
     *
     * @return an Optional price that contains the maximum price if it exists,
     * or an empty Optional if there are no prices in the database
     */
    @Query("SELECT p FROM Price p WHERE p.price = (SELECT MAX(price) FROM Price)")
    Optional<Price> findMaxPrice();

    /**
     * Returns
     *
     * <p>The method executes a custom query to find an average price for the whole database.</p>
     *
     * @return Optional Double that contains an average price if it exists,
     * or an empty Optional if there are no prices in the database.
     */
    @Query(nativeQuery = true, value = "SELECT AVG(price) FROM price")
    Optional<Double> averagePrice();
}
