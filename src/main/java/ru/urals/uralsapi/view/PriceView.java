package ru.urals.uralsapi.view;

import lombok.extern.slf4j.Slf4j;
import ru.urals.uralsapi.controller.PriceController;
import ru.urals.uralsapi.viewmodel.PriceChartViewModel;

import javax.swing.*;
import java.awt.*;

/**
 * Class responsible for setting up the user interface for the application.
 * It creates and configures the view and the view model for the price chart, and sets up the JFrame to display the chart.
 */
@Slf4j
public class PriceView {

    /**
     * This method configures the view for the price chart. It creates the view and the view model,
     * and adds them to a JFrame. The JFrame is then packed and made visible.
     *
     * @param priceController The controller that this view will interact with.
     */
    public void configure(PriceController priceController) {

        log.info("Creating the ViewModel and View");
        PriceChartViewModel viewModel = new PriceChartViewModel(priceController);
        PriceChartView view = new PriceChartView(viewModel);

        log.info("Creating a JFrame and adding the View to it");
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Crude Oil Urals Prices");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());
            frame.getContentPane().add(view, BorderLayout.CENTER);
            frame.add(new AddPricePanel(viewModel), BorderLayout.EAST);
            frame.pack();
            frame.setVisible(true);
        });
    }
}
