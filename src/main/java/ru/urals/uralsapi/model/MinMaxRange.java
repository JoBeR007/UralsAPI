package ru.urals.uralsapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

/**
 * Class for writing statistics into it.
 *
 * <p>Contains fields for minimum and maximum prices,
 * corresponding dates and total amount of entries in repository</p>
 */
public class MinMaxRange {
    @JsonProperty("min")
    private Double min;
    @JsonProperty("max")
    private Double max;
    @JsonProperty("minDate")
    private LocalDate minDate;
    @JsonProperty("maxDate")
    private LocalDate maxDate;
    @JsonProperty("amount of entries")
    private Long amount;

    public MinMaxRange(Double min, LocalDate minDate,
                       Double max, LocalDate maxDate, Long amount) {
        this.min = min;
        this.minDate = minDate;
        this.max = max;
        this.maxDate = maxDate;
        this.amount = amount;
    }

    public MinMaxRange(Double min, Double max, Long amount) {
        this.min = min;
        this.max = max;
        this.amount = amount;
    }

    public MinMaxRange(Double min, Double max) {
        this.min = min;
        this.max = max;
    }

    public MinMaxRange() {
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public LocalDate getMinDate() {
        return minDate;
    }

    public void setMinDate(LocalDate minDate) {
        this.minDate = minDate;
    }

    public LocalDate getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(LocalDate maxDate) {
        this.maxDate = maxDate;
    }
}
