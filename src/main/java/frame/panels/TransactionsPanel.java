package frame.panels;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.time.LocalDate;

public class TransactionsPanel extends JPanel{
    private final String[] currencyTypes;
    private JPanel newTransactionPanel;
    private JTable transactionTable;
    private JTextField vendorInput;
    private JComboBox<String> transactionTypeInput;
    private JTextField quantityInput;
    private JTextField rateInput;
    private JComboBox<String> foreignCurrencyInput;
    public JTextField transactionDateInput;
    public DatePicker maturityDateInput;
    private DatePickerSettings maturityDateSettings;

    private Connection connection;

    public TransactionsPanel(String[] currencyTypes, Connection connection){
        this.currencyTypes = currencyTypes;
        this.connection = connection;

        createTransactionsTab();
        fillTransactionsTab();
    }

// todo: use JFormattedTextField

    private void createTransactionsTab()
    {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    }

    private void fillTransactionsTab()
    {
        JLabel reportLabel = new JLabel();
        reportLabel.setText("Overall position: 10000 USD");
        add(reportLabel);

        addNewTransactionPanel();

        addTransactionsTable();
    }

    private void addNewTransactionPanel()
    {
        newTransactionPanel = new JPanel(new FlowLayout());

        addDate();
        addVendor();
        addType();
        addQuant();
        addForeignCurrency();
        addRate();
        addMaturity();
        addAddButton();

        add(newTransactionPanel);
    }

    private void addDate()
    {
        JPanel transactionDatePanel = new JPanel();
        transactionDatePanel.setLayout(new BoxLayout(transactionDatePanel, BoxLayout.PAGE_AXIS));
        JLabel transactionDate = new JLabel("Transaction Date");
        transactionDateInput = new JTextField();
        transactionDateInput.setText(String.valueOf(LocalDate.now()));
        transactionDateInput.setEditable(false);
        transactionDatePanel.add(transactionDate);
        transactionDatePanel.add(transactionDateInput);
        newTransactionPanel.add(transactionDatePanel);
    }

    private void addVendor()
    {
        JPanel vendorPanel = new JPanel();
        vendorPanel.setLayout(new BoxLayout(vendorPanel, BoxLayout.PAGE_AXIS));
        JLabel vendor = new JLabel("Vendor");
        vendorInput = new JTextField();
        vendorPanel.add(vendor);
        vendorPanel.add(vendorInput);
        newTransactionPanel.add(vendorPanel);
    }

    private void addType()
    {
        JPanel transactionTypePanel = new JPanel();
        transactionTypePanel.setLayout(new BoxLayout(transactionTypePanel, BoxLayout.PAGE_AXIS));
        JLabel transactionType = new JLabel("Transaction Type");
        String[] transactionTypes = {"Spot", "Future"};
        transactionTypeInput = new JComboBox<>(transactionTypes);
        transactionTypeInput.setSelectedIndex((0));
        transactionTypePanel.add(transactionType);
        transactionTypePanel.add(transactionTypeInput);
        newTransactionPanel.add(transactionTypePanel);
    }

    private void addQuant()
    {
        JPanel quantityPanel = new JPanel();
        quantityPanel.setLayout(new BoxLayout(quantityPanel, BoxLayout.PAGE_AXIS));
        JLabel quantity = new JLabel("Quantity");
        quantityInput = new JTextField();
        quantityPanel.add(quantity);
        quantityPanel.add(quantityInput);
        newTransactionPanel.add(quantityPanel);
    }

    private void addForeignCurrency()
    {
        JPanel foreignCurrencyPanel = new JPanel();
        foreignCurrencyPanel.setLayout(new BoxLayout(foreignCurrencyPanel, BoxLayout.PAGE_AXIS));
        JLabel foreignCurrency = new JLabel("Foreign Currency");
        foreignCurrencyInput = new JComboBox<>(currencyTypes);
        foreignCurrencyPanel.add(foreignCurrency);
        foreignCurrencyPanel.add(foreignCurrencyInput);
        newTransactionPanel.add(foreignCurrencyPanel);
    }

    private void addRate()
    {
        JPanel ratePanel = new JPanel();
        ratePanel.setLayout(new BoxLayout(ratePanel, BoxLayout.PAGE_AXIS));
        JLabel rate = new JLabel("Rate");
        rateInput = new JTextField();
        ratePanel.add(rate);
        ratePanel.add(rateInput);
        newTransactionPanel.add(ratePanel);
    }

    private void addMaturity()
    {
        // TODO: disable if spot is selected
        JPanel maturityDatePanel = new JPanel();
        maturityDatePanel.setLayout(new BoxLayout(maturityDatePanel, BoxLayout.PAGE_AXIS));
        JLabel maturityDate = new JLabel("Maturity Date");
        maturityDateSettings = new DatePickerSettings();
        maturityDateInput = new DatePicker(maturityDateSettings);
        maturityDateSettings.setDateRangeLimits(LocalDate.now().plusDays(1), LocalDate.now().plusYears(100));
        maturityDateInput.setDate(LocalDate.now().plusDays(1));
        maturityDatePanel.add(maturityDate);
        maturityDatePanel.add(maturityDateInput);
        newTransactionPanel.add(maturityDatePanel);
    }

    private void addAddButton()
    {
        JButton add = new JButton("Add Transaction");
        add.addActionListener(this::onAddClicked);
        newTransactionPanel.add(add);
    }

    public void onAddClicked(ActionEvent event)
    {
        if (fieldsAreValid() && hasEnoughMoney())
        {
            DefaultTableModel model = (DefaultTableModel) transactionTable.getModel();
            model.addRow(new Object[]{transactionDateInput.getText(),
                    vendorInput.getText(),
                    transactionTypeInput.getSelectedItem(),
                    quantityInput.getText(),
                    foreignCurrencyInput.getSelectedItem(),
                    rateInput.getText(),
                    maturityDateInput.getText(),
                    "rate goes here"});

            // todo: add to database

            clearFields();
        }
        else
        {
            sendErrorMessage();
        }
    }

    private void sendErrorMessage()
    {
        JOptionPane.showMessageDialog(this, "Please ensure all fields are valid",
                "Error message", JOptionPane.ERROR_MESSAGE);
    }

    private boolean hasEnoughMoney()
    {
        // TODO: implement
        return true;
    }

    private boolean fieldsAreValid()
    {

        // todo: decide if we're letting the vendor be empty

        boolean validQuant;
        try
        {
            double quant = Double.parseDouble(quantityInput.getText());
            validQuant = quant > 0;
            // todo: add other conditions?
        } catch (Exception e)
        {
            validQuant = false;
        }

        boolean validRate;
        try
        {
            double rate = Double.parseDouble(rateInput.getText());
            validRate = true;
            // todo: add other conditions?
        } catch (Exception e)
        {
            validRate = false;
        }

        boolean validMaturityDate = maturityDateInput.getDate() != null;

        return validQuant
                && validRate
                && validMaturityDate;
    }

    private void clearFields()
    {
        vendorInput.setText("");
        quantityInput.setText("");
        rateInput.setText("");
        maturityDateInput.setText("");
    }

    private void addTransactionsTable()
    {
        // todo: add transactions from database
        String[] columnNames = {"Transaction Date",
                "Vendor",
                "Type",
                "Quantity",
                "Foreign Currency",
                "Rate",
                "Maturity Date",
                "Implied Risk-Free Rate"};

        int numRows = 0;
        DefaultTableModel model = new DefaultTableModel(numRows, columnNames.length);
        model.setColumnIdentifiers(columnNames);

        transactionTable = new JTable(model);
        JScrollPane sp = new JScrollPane(transactionTable);
        sp.setBounds(25, 50, 300, 300);
        add(sp);
    }

}
