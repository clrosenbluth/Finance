package frame.panels;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;
import frame.StoredProcs;
import presenter.PNLPresenter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.awt.event.ActionEvent;
import java.time.LocalDate;

public class PNLPanel extends JPanel{

    private StoredProcs storedProcedures;
    private final String[] currencyTypes;
    //TODO: add "Download Report" button
    PNLPresenter pnlPresenter;

    //update as transactions are added - get values from presenter?
    private LocalDate firstTransactionDate;
    private LocalDate lastTransactionDate;

    private LocalDate firstDateFromGivenRange;
    private LocalDate lastDateFromGivenRange;
    private double overallPNL = 0.0; //since initial investment or overall pnl within given time frame

    private JLabel overallPNLLabel;
    private JPanel topPanel;
    private JPanel startDateRangePanel;
    private JPanel lastDateRangePanel;
    private JLabel startDateRangeLabel;
    private JLabel lastDateRangeLabel;
    private JTable pnlTable;
    private JButton refreshButton;
    private DatePickerSettings datePickerSettings;
    private DatePickerSettings datePickerSettings2;
    private DatePicker startDatePicker;
    private DatePicker lastDatePicker;

    public PNLPanel(String[] currencyTypes, StoredProcs storedProcedures){
        this.storedProcedures = storedProcedures;
        this.currencyTypes = currencyTypes;
        pnlPresenter = new PNLPresenter();

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

        startDateRangePanel = new JPanel();
        startDateRangePanel.setLayout(new BoxLayout(startDateRangePanel, BoxLayout.PAGE_AXIS));
        startDateRangeLabel = new JLabel("Start Date");
        startDateRangePanel.add(startDateRangeLabel);
        datePickerSettings = new DatePickerSettings();
        startDatePicker = new DatePicker(datePickerSettings);
        datePickerSettings.setDateRangeLimits(firstTransactionDate, lastTransactionDate);
        startDatePicker.setDate(firstTransactionDate);
        startDatePicker.addDateChangeListener(this::onDateChosen);
        startDateRangePanel.add(startDatePicker);

        lastDateRangePanel = new JPanel();
        lastDateRangePanel.setLayout(new BoxLayout(lastDateRangePanel, BoxLayout.PAGE_AXIS));
        lastDateRangeLabel = new JLabel("Last Date");
        lastDateRangePanel.add(lastDateRangeLabel);
        datePickerSettings2 = new DatePickerSettings();
        lastDatePicker = new DatePicker(datePickerSettings2);
        datePickerSettings2.setDateRangeLimits(lastDatePicker.getDate(), lastTransactionDate);
        lastDatePicker.setDate(lastTransactionDate);
        lastDatePicker.addDateChangeListener(this::onDateChosen);
        lastDateRangePanel.add(lastDatePicker);

        overallPNLLabel = new JLabel();
        overallPNLLabel.setText("Overall PNL = " + overallPNL + " USD");

        topPanel.add(startDateRangePanel);
        topPanel.add(lastDateRangePanel);
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
