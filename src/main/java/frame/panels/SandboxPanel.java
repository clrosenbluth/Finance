package frame.panels;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.time.LocalDate;

import Tools.Constants;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import presenter.SandboxPresenter;

public class SandboxPanel extends JPanel{
    private SandboxPresenter presenter;

    private final String[] currencyTypes;
    private JPanel newTransactionPanel;
    private JPanel tableButtonsPanel;
    private JTable transactionTable;
    private JTextField vendorInput;
    private JComboBox<String> transactionTypeInput;
    private JTextField quantityInput;
    private JTextField rateInput;
    private JComboBox<String> foreignCurrencyInput;

    private Connection connection;

    public DatePicker transactionDateInput;
    private DatePickerSettings transactionDateSettings;

    public DatePicker maturityDateInput;
    private DatePickerSettings maturityDateSettings;

    public SandboxPanel(String[] currencyTypes, Connection connection){
        this.currencyTypes = currencyTypes;
        this.presenter = new SandboxPresenter(this);
        this.connection = connection;

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
        reportLabel.setText(
                "Overall position: " +
                presenter.getNPV() +
                " USD"
        );
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

    public String getTransactionDate()
    {
        return transactionDateInput.getDate().toString();
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

    public String getVendor()
    {
        return vendorInput.getText();
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

    public String getType()
    {
        return transactionTypeInput.getSelectedItem().toString();
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

    public String getQuant()
    {
        return quantityInput.getText();
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

    public String getFX()
    {
        return foreignCurrencyInput.getSelectedItem().toString();
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

    public String getRate()
    {
        return rateInput.getText();
    }

    private void addMaturity()
    {
        JPanel maturityDatePanel = new JPanel();
        maturityDatePanel.setLayout(new BoxLayout(maturityDatePanel, BoxLayout.PAGE_AXIS));
        JLabel maturityDate = new JLabel("Maturity Date");
        maturityDateSettings = new DatePickerSettings();
        maturityDateInput = new DatePicker(maturityDateSettings);
        maturityDateSettings.setDateRangeLimits(LocalDate.now().minusDays(99), LocalDate.now().plusYears(100).plusDays(1));//date is after transaction date
        maturityDateInput.setDate(LocalDate.now().plusDays(1));
        maturityDateInput.setEnabled(transactionTypeInput.getSelectedItem().equals(Constants.FUTURE.label));
        maturityDatePanel.add(maturityDate);
        maturityDatePanel.add(maturityDateInput);
        newTransactionPanel.add(maturityDatePanel);
    }

    public String getMaturityDate()
    {
        return maturityDateInput.getDate().toString();
    }

    private void addAddButton()
    {
        JButton add = new JButton("Add Transaction");
        add.addActionListener(this::onAddClicked);
        newTransactionPanel.add(add);
    }

    public void addRowToModel(
            String transactionDate,
            String vendor,
            String type,
            String quant,
            String FX,
            String rate,
            String maturityDate,
            String impliedRate
    )
    {
        DefaultTableModel model = (DefaultTableModel) transactionTable.getModel();
        model.addRow(new Object[]{
                transactionDate,
                vendor,
                type,
                quant,
                FX,
                rate,
                maturityDate,
                impliedRate
        });
    }

    public void onAddClicked(ActionEvent event)
    {
        presenter.tryToAdd();
    }

    public void onTypeChange(ActionEvent event)
    {
        maturityDateInput.setEnabled(transactionTypeInput.getSelectedItem().equals(Constants.FUTURE.label));
    }

    public void sendErrorMessage()
    {
        JOptionPane.showMessageDialog(this, "Please ensure all fields are valid",
                "Error message", JOptionPane.ERROR_MESSAGE);
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

    public void clearFields()
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
