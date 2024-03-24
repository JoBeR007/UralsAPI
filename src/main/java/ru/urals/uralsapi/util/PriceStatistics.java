package ru.urals.uralsapi.util;

import ru.urals.uralsapi.model.Price;

import java.util.List;
import org.apache.commons.math3.stat.descriptive.moment.Skewness;

public class PriceStatistics {

    public static double calculateStandardDeviation(List<Price> prices) {
        double sum = 0.0, standardDeviation = 0.0;
        int length = prices.size();

        for(Price num : prices) {
            sum += num.getPrice();
        }

        double mean = sum/length;

        for(Price num: prices) {
            standardDeviation += Math.pow(num.getPrice() - mean, 2);
        }

        return Math.sqrt(standardDeviation/length);
    }


    public static double calculateROC(List<Price> prices, int periods) {
        int size = prices.size();
        int actualPeriods = Math.min(size, periods);

        double oldPrice = prices.get(size - actualPeriods).getPrice();
        double newPrice = prices.get(size - 1).getPrice();

        return (newPrice - oldPrice) / oldPrice;
    }

    public static double calculateSkewness(List<Price> prices) {
        Skewness skewness = new Skewness();
        double[] pricesArray = prices.stream().mapToDouble(Price::getPrice).toArray();
        return skewness.evaluate(pricesArray);
    }

}

