package frame.panels;

import calculations.PNLTableValues;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;
import presenter.PNLPresenter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.awt.event.ActionEvent;
import java.time.LocalDate;

public class PNLPanel extends JPanel{

    private Connection connection;
    private final String[] currencyTypes;
    //TODO: add "Download Report" button
    PNLPresenter pnlPresenter;
    PNLTableValues tableValues;

    //update as transactions are added - get values from presenter?
    private LocalDate firstTransactionDate;
    private LocalDate lastTransactionDate;

    private LocalDate dateFromGivenRange;
    private double overallPNL = 0.0; //PnL from first transaction to chosen date

    private JLabel overallPNLLabel;
    private JPanel topPanel;
    private JPanel chooseDatePanel;
    private JLabel chooseDateLabel;
    private JTable pnlTable;
    private JButton refreshButton;
    private DatePickerSettings datePickerSettings;
    private DatePicker datePicker;

    public PNLPanel(String[] currencyTypes, Connection connection){
        this.connection = connection;
        this.currencyTypes = currencyTypes;
        this.tableValues = new PNLTableValues();
        this.pnlPresenter = new PNLPresenter(tableValues);

        createPNLTab();
        fillPNLTab();
    }

    private void createPNLTab() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    }

    private void fillPNLTab() {
        addRefreshButton();
        addTopPanel();
        createPNLTable();
    }

    private void addRefreshButton(){
        refreshButton = new JButton();
        refreshButton.setText("Refresh");

        refreshButton.addActionListener(this::onRefreshButtonClicked);

        add(refreshButton);
    }

    private void addTopPanel() {
        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        chooseDatePanel = new JPanel();
        chooseDatePanel.setLayout(new BoxLayout(chooseDatePanel, BoxLayout.PAGE_AXIS));
        chooseDateLabel = new JLabel("Last Date To View");
        chooseDatePanel.add(chooseDateLabel);
        datePickerSettings = new DatePickerSettings();
        datePicker = new DatePicker(datePickerSettings);
        datePickerSettings.setDateRangeLimits(firstTransactionDate, lastTransactionDate);
        datePicker.setDate(lastTransactionDate);
        datePicker.addDateChangeListener(this::onDateChosen);
        chooseDatePanel.add(datePicker);

        overallPNLLabel = new JLabel();
        overallPNLLabel.setText("Overall PNL = " + overallPNL + " USD");

        topPanel.add(chooseDatePanel);
        topPanel.add(Box.createHorizontalStrut(30));
        topPanel.add(overallPNLLabel);

        add(topPanel);
    }

    private void createPNLTable() {
        String[] columnNames = {
                "Date",
                "Total FX Position",
                "Remaining Investment",
                "PnL",

        };
        int rows = 0;
        DefaultTableModel model = new DefaultTableModel(rows, columnNames.length);
        model.setColumnIdentifiers(columnNames);

        pnlTable = new JTable(model);
        JScrollPane sp = new JScrollPane(pnlTable);
        sp.setBounds(25, 50, 300, 300);
        add(sp);
    }

    private void onRefreshButtonClicked(ActionEvent actionEvent) {
        //TODO: update values in pnl tab
    }

    private void onDateChosen(DateChangeEvent dateChangeEvent) {
        //TODO: for both date pickers
    }


}
