package ru.urals.uralsapi.view;

import lombok.extern.slf4j.Slf4j;
import ru.urals.uralsapi.model.Price;
import ru.urals.uralsapi.viewmodel.PriceChartViewModel;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

import static ru.urals.uralsapi.util.DateParser.parseDateFromString;

/**
 * The `AddPricePanel` class extends `JPanel` and is responsible for creating and managing the user interface for adding new prices.
 * It interacts with the `PriceChartViewModel` to add new prices.
 * It also maintains the fields for the date, price, open, high, and low values.
 *
 * @author Your Name
 * @version 1.0
 * @since 1.0
 */
@Slf4j
public class AddPricePanel extends JPanel {

    private final PriceChartViewModel viewModel;
    private JTextField dateField;
    private JTextField priceField;
    private JTextField openField;
    private JTextField highField;
    private JTextField lowField;
    private JButton submitButton;

    /**
     * Constructs a new `AddPricePanel` with the given `PriceChartViewModel`.
     * It sets up the layout, creates the fields for the date, price, open, high, and low values, and adds them to the panel.
     * It also creates a submit button, adds it to the panel, and adds an action listener to it.
     *
     * @param viewModel The view model that this panel will interact with.
     */
    public AddPricePanel(PriceChartViewModel viewModel) {
        this.viewModel = viewModel;
        log.info("Creating Panel for Adding Price");
        createFields();

        // Add an action listener to the submit button
        submitButton.addActionListener(e -> {
            try {
                LocalDate date = parseDateFromString(dateField.getText());
                Double price = Double.parseDouble(priceField.getText());
                Double open = Double.parseDouble(openField.getText());
                Double high = Double.parseDouble(highField.getText());
                Double low = Double.parseDouble(lowField.getText());
                boolean res = viewModel.addPrice(new Price(date, open, price, high, low));
                if (!res) {
                    JOptionPane.showMessageDialog(this,
                            "Price already present for given date",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                log.warn("NumberFormatException while trying to submit new price" + ex.getLocalizedMessage());
                JOptionPane.showMessageDialog(this,
                        "Some of the values are incorrect. Please check them!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                log.warn("Exception while trying to submit new price" + ex.getLocalizedMessage());
                JOptionPane.showMessageDialog(this,
                        "Incorrect values. Please try again! Date format is MM/dd/yyyy",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });


    }

    private void createFields() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // Create the fields and add them to the panel
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.LINE_END;
        this.add(new JLabel("Date:"), c);
        c.gridx = 1;
        c.anchor = GridBagConstraints.LINE_START;
        this.dateField = new JTextField(10);
        this.add(dateField, c);

        c.gridx = 0;
        c.gridy = 2;
        c.anchor = GridBagConstraints.LINE_END;
        this.add(new JLabel("Price:"), c);
        c.gridx = 1;
        c.anchor = GridBagConstraints.LINE_START;
        this.priceField = new JTextField(10);
        this.add(priceField, c);

        c.gridx = 0;
        c.gridy = 4;
        c.anchor = GridBagConstraints.LINE_END;
        this.add(new JLabel("Open:"), c);
        c.gridx = 1;
        c.anchor = GridBagConstraints.LINE_START;
        this.openField = new JTextField(10);
        this.add(openField, c);

        c.gridx = 0;
        c.gridy = 6;
        c.anchor = GridBagConstraints.LINE_END;
        this.add(new JLabel("High:"), c);
        c.gridx = 1;
        c.anchor = GridBagConstraints.LINE_START;
        this.highField = new JTextField(10);
        this.add(highField, c);

        c.gridx = 0;
        c.gridy = 8;
        c.anchor = GridBagConstraints.LINE_END;
        this.add(new JLabel("Low:"), c);
        c.gridx = 1;
        c.anchor = GridBagConstraints.LINE_START;
        this.lowField = new JTextField(10);
        this.add(lowField, c);


        // Create the submit button and add it to the panel
        c.gridx = 0;
        c.gridy = 10;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        this.submitButton = new JButton("Submit");
        this.add(submitButton, c);  // Add an empty label to fill the last grid cell
    }
}
