package frame.panels;

import Tools.Constants;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import presenter.TransactionPresenter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;

public class TransactionsPanel extends JPanel{
    private TransactionPresenter presenter;

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

    public TransactionsPanel(String[] currencyTypes){
        this.currencyTypes = currencyTypes;
        presenter = new TransactionPresenter(this);

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
        // todo: actually calculate this
        reportLabel.setText("Overall present value: " +
                presenter.getNPV() +
                " USD");
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
        String[] transactionTypes = {Constants.SPOT.label, Constants.FUTURE.label};
        transactionTypeInput = new JComboBox<>(transactionTypes);
        transactionTypeInput.setSelectedIndex((0));
        transactionTypeInput.addActionListener(this::onTypeChange);
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
        JPanel maturityDatePanel = new JPanel();
        maturityDatePanel.setLayout(new BoxLayout(maturityDatePanel, BoxLayout.PAGE_AXIS));
        JLabel maturityDate = new JLabel("Maturity Date");
        maturityDateSettings = new DatePickerSettings();
        maturityDateInput = new DatePicker(maturityDateSettings);
        maturityDateSettings.setDateRangeLimits(LocalDate.now().plusDays(1), LocalDate.now().plusYears(100));
        maturityDateInput.setDate(LocalDate.now().plusDays(1));
        maturityDateInput.setEnabled(transactionTypeInput.getSelectedItem().equals(Constants.FUTURE.label));
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
        presenter.tryToAdd();
    }

    public void onTypeChange(ActionEvent event)
    {
        maturityDateInput.setEnabled(transactionTypeInput.getSelectedItem().equals(Constants.FUTURE.label));
    }

    public void addRowToModel(String transactionDate,
                              String vendor,
                              String transactionType,
                              String quantity,
                              String foreignCurrency,
                              String rate,
                              String maturity,
                              String impliedRate)
    {
        DefaultTableModel model = (DefaultTableModel) transactionTable.getModel();
        model.addRow(new Object[]{
                transactionDate,
                vendor,
                transactionType,
                quantity,
                foreignCurrency,
                rate,
                maturity,
                impliedRate
        });
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
                "Implied Risk-Free Rate"};

        // todo: fill from database

        int numRows = 0;
        DefaultTableModel model = new DefaultTableModel(numRows, columnNames.length){
            @Override
            public  boolean isCellEditable(int row, int column){
                return false;
            }
        };
        model.setColumnIdentifiers(columnNames);

        transactionTable = new JTable(model);
        JScrollPane jsp = new JScrollPane(transactionTable);
        jsp.setBounds(25, 50, 300, 300);
        add(jsp);


    }

    public void sendErrorMessage()
    {
        JOptionPane.showMessageDialog(this, "Please ensure all fields are valid",
                "Error message", JOptionPane.ERROR_MESSAGE);
    }

    public String getTransactionDate()
    {
        return transactionDateInput.getText();
    }

    public String getVendor()
    {
        return vendorInput.getText();
    }

    public String getType()
    {
        return (String) transactionTypeInput.getSelectedItem();
    }

    public String getQuant()
    {
        return quantityInput.getText();
    }

    public String getFX()
    {
        return (String) foreignCurrencyInput.getSelectedItem();
    }

    public String getRate()
    {
        return rateInput.getText();
    }

    public String getMaturityDate()
    {
        return maturityDateInput.getDate().toString();
    }

    public void clearFields()
    {
        vendorInput.setText("");
        quantityInput.setText("");
        rateInput.setText("");
        maturityDateInput.setText("");
    }
}
