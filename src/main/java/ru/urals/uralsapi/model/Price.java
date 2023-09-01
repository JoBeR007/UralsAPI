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


    private LocalDate date;
    private Double price;
    private Double open;
    private Double high;
    private Double low;
    private String change;
    private @Id
    @GeneratedValue(strategy = GenerationType.AUTO) Long id;

    public Price(LocalDate date, Double price, Double open,
                 Double high, Double low, String change) {
        this.date = date;
        this.price = price;
        this.open = open;
        this.high = high;
        this.low = low;
        this.change = change;
    }

    public Price(LocalDate date, Double price) {
        this.date = date;
        this.price = price;
    }

    public Price() {}

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date123) {
        this.date = date123;
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

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price1 = (Price) o;
        return id.equals(price1.id) && price.equals(price1.price) && date.equals(price1.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, date);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Price{");
        sb.append("date=").append(date);
        sb.append(", price=").append(price);
        sb.append(", open=").append(open);
        sb.append(", high=").append(high);
        sb.append(", low=").append(low);
        sb.append(", change='").append(change).append('\'');
        sb.append(", id=").append(id);
        sb.append('}');
        return sb.toString();
    }
}
