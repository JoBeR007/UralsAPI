package ru.urals.uralsapi.view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.DefaultHighLowDataset;
import ru.urals.uralsapi.model.Price;
import ru.urals.uralsapi.viewmodel.PriceChartViewModel;

import javax.swing.*;
import java.awt.*;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * The `PriceChartView` class extends `JPanel` and is responsible for creating and managing the user interface for the price chart.
 * It interacts with the `PriceChartViewModel` to fetch and update the prices.
 * It also maintains the start and end time for which the prices are to be fetched.
 *
 * @author Your Name
 * @version 1.0
 * @since 1.0
 */
public class PriceChartView extends JPanel {
    private final PriceChartViewModel viewModel;
    private JFreeChart chart;
    private ChartPanel chartPanel;
    private JSpinner startDateSpinner;
    private JSpinner endDateSpinner;


    /**
     * Constructs a new `PriceChartView` with the given `PriceChartViewModel`.
     * It sets up the layout, creates the chart and adds it to a `ChartPanel`.
     * It also creates the controls for the start and end date and adds them to a separate panel.
     *
     * @param viewModel The view model that this view will interact with.
     */
    public PriceChartView(PriceChartViewModel viewModel) {
        this.viewModel = viewModel;
        this.setLayout(new BorderLayout());

        // Create the chart and add it to a ChartPanel
        this.chart = createChart();
        this.chartPanel = new ChartPanel(chart);
        this.add(chartPanel, BorderLayout.CENTER);

        // Create the controls and add them to a separate panel
        JPanel controlPanel = new JPanel();
        this.startDateSpinner = new JSpinner(new SpinnerDateModel());
        this.endDateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor startDateEditor = new JSpinner.DateEditor(startDateSpinner, "MM/dd/yyyy");
        JSpinner.DateEditor endDateEditor = new JSpinner.DateEditor(endDateSpinner, "MM/dd/yyyy");
        startDateSpinner.setEditor(startDateEditor);
        endDateSpinner.setEditor(endDateEditor);

        controlPanel.add(new JLabel("Start Date:"));
        controlPanel.add(startDateSpinner);
        controlPanel.add(new JLabel("End Date:"));
        controlPanel.add(endDateSpinner);
        this.add(controlPanel, BorderLayout.SOUTH);

        startDateSpinner.addChangeListener(e -> {
            Date date = (Date) startDateSpinner.getValue();
            viewModel.setStartTime(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            refresh();
        });

        endDateSpinner.addChangeListener(e -> {
            Date date = (Date) endDateSpinner.getValue();
            viewModel.setEndTime(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            refresh();
        });
    }

    /**
     * Creates a candlestick chart with the data from the `PriceChartViewModel`.
     * It customizes the plot and sets the background color of the chart.
     *
     * @return The created `JFreeChart`.
     */
    private JFreeChart createChart() {
        // Create a default high-low dataset
        DefaultHighLowDataset dataset = createDataset();

        // Create the candlestick chart
        JFreeChart chart = ChartFactory.createCandlestickChart(
                "Crude Oil Urals Prices", // title
                "Date", // x-axis label
                "Price", // y-axis label
                dataset, // data
                false // create legend?
        );

        // Set the background color of the chart
        chart.setBackgroundPaint(Color.decode("#858596"));

        // Customize the plot
        XYPlot plot = (XYPlot) chart.getPlot();
        NumberAxis rangeAxis = new NumberAxis("Price");
        rangeAxis.setAutoRangeIncludesZero(false);
        DateAxis domainAxis = new DateAxis("Date");
        plot.setRangeAxis(rangeAxis);
        plot.setDomainAxis(domainAxis);
        plot.setBackgroundPaint(Color.decode("#121420"));
        plot.setDomainGridlinePaint(Color.decode("#2C2B3C"));
        plot.setRangeGridlinePaint(Color.decode("#403F4C"));

        // Return the chart
        return chart;
    }

    /**
     * Creates a `DefaultHighLowDataset` with the prices from the `PriceChartViewModel`.
     *
     * @return The created `DefaultHighLowDataset`.
     */
    private DefaultHighLowDataset createDataset() {
        // Get the prices from the viewModel
        List<Price> prices = viewModel.getPrices();

        // Create arrays to hold the high, low, open, close, and volume data
        int numPrices = prices.size();
        Date[] date = new Date[numPrices];
        double[] high = new double[numPrices];
        double[] low = new double[numPrices];
        double[] open = new double[numPrices];
        double[] avg = new double[numPrices];
        double[] volume = new double[numPrices];

        // Populate the arrays with data from the prices
        for (int i = 0; i < numPrices; i++) {
            Price price = prices.get(i);
            date[i] = java.sql.Date.valueOf(price.getDate());
            high[i] = price.getHigh();
            low[i] = price.getLow();
            open[i] = price.getOpen();
            avg[i] = price.getPrice();
            volume[i] = 0.0; // volume data is not used in this example
        }

        // Create and return a new high-low dataset
        return new DefaultHighLowDataset("Price Data", date, high, low, open, avg, volume);
    }

    /**
     * Refreshes the chart by getting the updated data from the `PriceChartViewModel` and creating a new dataset.
     * If the dataset is empty, it displays an error message.
     */
    public void refresh() {
        // Get the updated data from the viewModel
        DefaultHighLowDataset dataset = createDataset();

        // Check if the dataset is empty
        if (dataset.getItemCount(0) == 0) {
            // If the dataset is empty, display an error message
            JOptionPane.showMessageDialog(this,
                    "No prices available for the selected date range.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            // If the dataset is not empty, update the chart's dataset
            XYPlot plot = (XYPlot) chart.getPlot();
            plot.setDataset(dataset);

            // Repaint the chart to reflect the new data
            chartPanel.repaint();
        }
    }

}
