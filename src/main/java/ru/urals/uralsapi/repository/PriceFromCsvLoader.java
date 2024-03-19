package ru.urals.uralsapi.repository;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import ru.urals.uralsapi.model.Headers;
import ru.urals.uralsapi.model.Price;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * A class for parsing prices from a CSV file and uploading it into a price repository.
 *
 * <p>The class uses the Apache Commons CSV library to parse the CSV file.
 * It assumes that the CSV file has a header row with column names
 * that may not match the fields of the entity,
 * therefore it uses Headers enum with defined headers</p>
 */
@Slf4j
public class PriceFromCsvLoader {

    /**
     * Parses data from the given CSV file and uploads it into the repository.
     *
     * @param fileName path to file
     * @param repository repository that is used for uploading prices
     * @param dateFormat string dateFormat that is used in the CSV file
     */
    public static void LoadPricesFromCsv(String fileName, PriceRepository repository, String dateFormat) {
        DateTimeFormatter form = DateTimeFormatter.ofPattern(dateFormat).withLocale(Locale.US);
        try {
            log.info("Reading CSV file");
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.builder()
                    .setSkipHeaderRecord(true).setHeader(Headers.class)
                    .build().parse(new FileReader(fileName));
            for (CSVRecord record : records) {
                Price price = new Price(LocalDate.parse(record.get(Headers.DATE), form),
                        Double.parseDouble(record.get(Headers.PRICE)),
                        Double.parseDouble(record.get(Headers.OPEN)) - 1,
                        Double.parseDouble(record.get(Headers.HIGH)) + 2,
                        Double.parseDouble(record.get(Headers.LOW)) - 2);
                repository.save(price);
            }
        } catch (FileNotFoundException ex) {
            log.error("Could not find the CSV file: " + ex.getMessage());
        } catch (IOException ex) {
            log.error("Error reading the CSV file: " + ex.getMessage());
        }
    }
}
