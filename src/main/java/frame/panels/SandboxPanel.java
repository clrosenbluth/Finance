package frame.panels;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

public class SandboxPanel extends JPanel{
    private final String[] currencyTypes;
    private JPanel newTransactionPanel;
    private JPanel tableButtonsPanel;
    private JTable transactionTable;
    private JTextField vendorInput;
    private JComboBox<String> transactionTypeInput;
    private JTextField quantityInput;
    private JTextField rateInput;
    private JComboBox<String> foreignCurrencyInput;

    public DatePicker transactionDateInput;
    private DatePickerSettings transactionDateSettings;

    public DatePicker maturityDateInput;
    private DatePickerSettings maturityDateSettings;

    public SandboxPanel(String[] currencyTypes){
        this.currencyTypes = currencyTypes;

        createSandboxTab();
        fillSandboxTab();
    }

    private void createSandboxTab()
    {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    }

    private void fillSandboxTab()
    {
        JLabel reportLabel = new JLabel();
        reportLabel.setText("Overall position: 10000 USD");
        add(reportLabel);

        addNewTransactionPanel();

        addTransactionsTable();

        addTableButtons();
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
        transactionDateSettings = new DatePickerSettings();
        transactionDateInput = new DatePicker(transactionDateSettings);
        transactionDateSettings.setDateRangeLimits(LocalDate.now().minusDays(100), LocalDate.now().plusYears(1000));
        transactionDateInput.setDateToToday();
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
        maturityDateSettings.setDateRangeLimits(LocalDate.now().minusDays(99), LocalDate.now().plusYears(100).plusDays(1));//date is after transaction date
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
                    "rate goes here",
            });
            // todo: use rate calculator

            clearFields();
        }
        else
        {
            sendErrorMessage();
        }
    }

    private void addTableButtons() {
        tableButtonsPanel = new JPanel(new FlowLayout());

        JButton removeSelectedRow = new JButton("Remove Selected Row");
        removeSelectedRow.addActionListener(this::onRemoveRowClicked);
        tableButtonsPanel.add(removeSelectedRow);

        JButton clearTable = new JButton("Clear Table");
        clearTable.addActionListener(this::clearTableClicked);
        tableButtonsPanel.add(clearTable);

        JButton loadTable = new JButton("Load Table From Database");
        tableButtonsPanel.add(loadTable);

        add(tableButtonsPanel);

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
        boolean validTransactionDate = transactionDateInput.getText() != null;
        // todo: also confirm that the date is today or earlier

        // todo: decide if we're letting the vendor be empty

        boolean validForwardQuant;
        try
        {
            double forwardQuant = Double.parseDouble(quantityInput.getText());
            validForwardQuant = forwardQuant > 0;
            // todo: add other conditions?
        } catch (Exception e)
        {
            validForwardQuant = false;
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

        boolean validMaturityDate = maturityDateInput.getDate() != null && maturityDateInput.getDate().isAfter(transactionDateInput.getDate());

        return validTransactionDate
                && validForwardQuant
                && validRate
                && validMaturityDate;
    }

    private void clearFields()
    {
        transactionDateInput.setText("");
        vendorInput.setText("");
        quantityInput.setText("");
        rateInput.setText("");
        maturityDateInput.setText("");
    }

    private void addTransactionsTable()
    {
        String[] columnNames = {"Transaction Date",
                "Vendor",
                "Type",
                "Quantity",
                "Foreign Currency",
                "Rate",
                "Maturity Date",
                "Implied Risk-Free Rate",
        };

        int numRows = 0;
        DefaultTableModel model = new DefaultTableModel(numRows, columnNames.length);
        model.setColumnIdentifiers(columnNames);

        transactionTable = new JTable(model);
        transactionTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        JScrollPane sp = new JScrollPane(transactionTable);
        sp.setBounds(25, 50, 300, 300);
        add(sp);

    }

    private void clearTableClicked(ActionEvent actionEvent) {
        DefaultTableModel dtm = (DefaultTableModel) transactionTable.getModel();
        dtm.setRowCount(0);
    }

    private void onRemoveRowClicked(ActionEvent actionEvent) {
        if(transactionTable.getSelectedRow() != -1) {
            // remove selected row from the model
            ((DefaultTableModel)transactionTable.getModel()).removeRow(transactionTable.getSelectedRow());
        }
    }

}
