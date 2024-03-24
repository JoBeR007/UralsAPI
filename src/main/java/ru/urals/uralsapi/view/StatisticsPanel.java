package ru.urals.uralsapi.view;

import org.apache.commons.math3.stat.regression.SimpleRegression;
import ru.urals.uralsapi.model.Price;
import ru.urals.uralsapi.util.ExponentialSmoothing;
import ru.urals.uralsapi.util.MovingAverage;
import ru.urals.uralsapi.util.PriceStatistics;
import ru.urals.uralsapi.viewmodel.PriceChartViewModel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StatisticsPanel extends JPanel {
    private final PriceChartViewModel viewModel;
    private JLabel minLabel;
    private JLabel maxLabel;
    private JLabel avgLabel;
    private JLabel difLabel;
    private JLabel stdDiv;
    private JLabel roc;
    private JLabel skewness;
    private JLabel regressionPredictionLabel;
    private JLabel movingPredictionLabel;
    private JLabel expPredictionLabel50;
    private JLabel expPredictionLabel25;
    private JLabel expPredictionLabel75;

    public StatisticsPanel(PriceChartViewModel viewModel) {
        this.viewModel = viewModel;
        this.setLayout(new GridLayout(0, 4));

        this.minLabel = new JLabel();
        this.maxLabel = new JLabel();
        this.avgLabel = new JLabel();
        this.stdDiv = new JLabel();
        this.roc = new JLabel();
        this.skewness = new JLabel();
        this.regressionPredictionLabel = new JLabel();
        this.movingPredictionLabel = new JLabel();
        this.expPredictionLabel50 = new JLabel();

        this.minLabel = createLabel("Min:");
        this.maxLabel = createLabel("Max:");
        this.avgLabel = createLabel("Avg:");
        this.difLabel = createLabel("Dif:");
        this.stdDiv = createLabel("Standard Deviation:");
        this.roc = createLabel("ROC (4 day period):");
        this.skewness = createLabel("Skewness:");

        this.regressionPredictionLabel = createLabel("LR Prediction:");
        this.movingPredictionLabel = createLabel("MA Prediction:");
        this.expPredictionLabel75 = createLabel("ES (alpha=0.75) Prediction:");
        this.expPredictionLabel50 = createLabel("ES (alpha=0.5) Prediction:");
        this.expPredictionLabel25 = createLabel("ES (alpha=0.25) Prediction:");

        refresh();
    }

    public void refresh() {
        List<Price> prices = viewModel.getPrices();
        if (!prices.isEmpty()) {
            double min = prices.stream().mapToDouble(Price::getPrice).min().getAsDouble();
            double max = prices.stream().mapToDouble(Price::getPrice).max().getAsDouble();
            double avg = prices.stream().mapToDouble(Price::getPrice).average().getAsDouble();

            minLabel.setText(String.format("%.2f", min));
            maxLabel.setText(String.format("%.2f", max));
            avgLabel.setText(String.format("%.2f", avg));
            difLabel.setText(String.format("%.2f", max - min));
            stdDiv.setText(String.format("%.2f", PriceStatistics.calculateStandardDeviation(prices)));
            roc.setText(String.format("%.2f", PriceStatistics.calculateROC(prices, 4)));
            skewness.setText(String.format("%.2f", PriceStatistics.calculateSkewness(prices)));

            MovingAverage ma = new MovingAverage(prices.size());
            SimpleRegression regression = new SimpleRegression();
            ExponentialSmoothing exp75 = new ExponentialSmoothing(0.75);
            ExponentialSmoothing exp50 = new ExponentialSmoothing(0.5);
            ExponentialSmoothing exp25 = new ExponentialSmoothing(0.25);
            for (int i = 0; i < prices.size(); i++) {
                double pr = prices.get(i).getPrice();
                regression.addData(i, pr);
                ma.add(pr);
                exp75.add(pr);
                exp50.add(pr);
                exp25.add(pr);
            }
            double regressionPrediction = regression.predict(prices.size());
            double movingPrediction = ma.predictNext();
            double expPrediction75 = exp75.predict();
            double expPrediction50 = exp50.predict();
            double expPrediction25 = exp25.predict();
            regressionPredictionLabel.setText(String.format("%.2f", regressionPrediction));
            movingPredictionLabel.setText(String.format("%.2f", movingPrediction));
            expPredictionLabel75.setText(String.format("%.2f", expPrediction75));
            expPredictionLabel50.setText(String.format("%.2f", expPrediction50));
            expPredictionLabel25.setText(String.format("%.2f", expPrediction25));
        } else {
            minLabel.setText("N/A");
            maxLabel.setText("N/A");
            avgLabel.setText("N/A");
            difLabel.setText("N/A");
            stdDiv.setText("N/A");
            roc.setText("N/A");
            regressionPredictionLabel.setText("N/A");
            movingPredictionLabel.setText("N/A");
            expPredictionLabel75.setText("N/A");
            expPredictionLabel50.setText("N/A");
            expPredictionLabel25.setText("N/A");
        }
    }

    private JLabel createLabel(String title) {
        JLabel label = new JLabel();
        label.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK), title));
        this.add(label);
        return label;
    }
}

