package frame.panels;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SandboxPanel extends JPanel{
    private final String[] currencyTypes;
    private JPanel newTransactionPanel;
    private JTable transactionTable;
    private JTextField transactionDateInput;
    private JTextField vendorInput;
    private JComboBox<String> transactionTypeInput;
    private JTextField forwardQuantityInput;
    private JTextField rateInput;
    private JTextField maturityDateInput;
    private JTextField forwardRateInput;
    private JComboBox<String> foreignCurrencyInput;

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
    }

    private void addNewTransactionPanel()
    {
        newTransactionPanel = new JPanel(new FlowLayout());

        addDate();
        addVendor();
        addType();
        addForwardQuant();
        addForeignCurrency();
        addRate();
        addMaturity();
        addForwardRate();
        addAddButton();

        add(newTransactionPanel);
    }

    private void addDate()
    {
        JPanel transactionDatePanel = new JPanel();
        transactionDatePanel.setLayout(new BoxLayout(transactionDatePanel, BoxLayout.PAGE_AXIS));
        JLabel transactionDate = new JLabel("Transaction Date");
        transactionDateInput = new JTextField();
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

    private void addForwardQuant()
    {
        JPanel forwardQuantityPanel = new JPanel();
        forwardQuantityPanel.setLayout(new BoxLayout(forwardQuantityPanel, BoxLayout.PAGE_AXIS));
        JLabel forwardQuantity = new JLabel("Forward Quantity");
        forwardQuantityInput = new JTextField();
        forwardQuantityPanel.add(forwardQuantity);
        forwardQuantityPanel.add(forwardQuantityInput);
        newTransactionPanel.add(forwardQuantityPanel);
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
        maturityDateInput = new JTextField();
        maturityDatePanel.add(maturityDate);
        maturityDatePanel.add(maturityDateInput);
        newTransactionPanel.add(maturityDatePanel);
    }

    private void addForwardRate()
    {
        // TODO: disable if spot is selected
        JPanel forwardRatePanel = new JPanel();
        forwardRatePanel.setLayout(new BoxLayout(forwardRatePanel, BoxLayout.PAGE_AXIS));
        JLabel forwardRate = new JLabel("Forward Rate");
        forwardRateInput = new JTextField();
        forwardRatePanel.add(forwardRate);
        forwardRatePanel.add(forwardRateInput);
        newTransactionPanel.add(forwardRatePanel);
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
                    forwardQuantityInput.getText(),
                    foreignCurrencyInput.getSelectedItem(),
                    rateInput.getText(),
                    maturityDateInput.getText(),
                    forwardRateInput.getText(),
                    "rate goes here"});

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
        boolean validTransactionDate = transactionDateInput.getText() != null;
        // todo: also confirm that the date is today or earlier

        // todo: decide if we're letting the vendor be empty

        boolean validForwardQuant;
        try
        {
            double forwardQuant = Double.parseDouble(forwardQuantityInput.getText());
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

        boolean validMaturityDate = maturityDateInput.getText() != null;
        // todo: also confirm that the date is after today

        boolean validForwardRate;
        try
        {
            double rate = Double.parseDouble(forwardRateInput.getText());
            validForwardRate = true;
            // todo: add other conditions?
        } catch (Exception e)
        {
            validForwardRate = false;
        }

        return validTransactionDate
                && validForwardQuant
                && validRate
                && validMaturityDate
                && validForwardRate;
    }

    private void clearFields()
    {
        transactionDateInput.setText("");
        vendorInput.setText("");
        forwardQuantityInput.setText("");
        rateInput.setText("");
        maturityDateInput.setText("");
        forwardRateInput.setText("");
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
                "Forward Rate",
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
