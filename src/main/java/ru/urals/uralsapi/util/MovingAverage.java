package ru.urals.uralsapi.util;

import java.util.ArrayDeque;
import java.util.Queue;

public class MovingAverage {
    private final Queue<Double> window = new ArrayDeque<>();
    private final int period;
    private double sum;

    public MovingAverage(int period) {
        assert period > 0 : "Period must be a positive integer!";
        this.period = period;
    }

    public void add(double num) {
        sum += num;
        window.add(num);
        if (window.size() > period) {
            sum -= window.remove();
        }
    }

    public double getAverage() {
        if (window.isEmpty()) return 0; // technically the average is undefined
        return sum / window.size();
    }

    public double predictNext() {
        // Predicts the next price to be the same as the current moving average
        return getAverage();
    }
}

