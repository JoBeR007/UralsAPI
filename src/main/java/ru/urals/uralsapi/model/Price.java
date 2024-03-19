package ru.urals.uralsapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents Price of Crude Oil Urals for specified date
 *
 * <p>Each price has a date, average price for that date, opening price,
 * highest price, lowest price, and change, compared to previous day</p>
 */
@Entity
public class Price {

    @Id
    private LocalDate date;
    private Double price;
    private Double open;
    private Double high;
    private Double low;

    public Price(LocalDate date, Double price, Double open,
                 Double high, Double low) {
        this.date = date;
        this.price = price;
        this.open = open;
        this.high = high;
        this.low = low;

    }

    public Price(LocalDate date, Double price) {
        this.date = date;
        this.price = price;
    }

    public Price() {}

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getOpen() {
        return open;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Price price1 = (Price) o;

        if (!date.equals(price1.date)) return false;
        if (!price.equals(price1.price)) return false;
        if (!open.equals(price1.open)) return false;
        if (!high.equals(price1.high)) return false;
        return low.equals(price1.low);
    }

    @Override
    public int hashCode() {
        int result = date.hashCode();
        result = 31 * result + price.hashCode();
        result = 31 * result + open.hashCode();
        result = 31 * result + high.hashCode();
        result = 31 * result + low.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Price{" + "date=" + date +
                ", price=" + price +
                ", open=" + open +
                ", high=" + high +
                ", low=" + low + '\'' +
                '}';
    }
}
