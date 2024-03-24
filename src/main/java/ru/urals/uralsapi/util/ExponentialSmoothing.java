package ru.urals.uralsapi.util;

public class ExponentialSmoothing {
    private final double alpha;
    private Double oldValue;

    public ExponentialSmoothing(double alpha) {
        this.alpha = alpha;
    }

    public void add(double value) {
        if (oldValue == null) {
            oldValue = value;
        }
        oldValue = oldValue + alpha * (value - oldValue);
    }

    public double predict(){
        return oldValue;
    }
}

