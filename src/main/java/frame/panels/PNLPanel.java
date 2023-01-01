package frame.panels;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;

public class PNLPanel extends JPanel{

    private Connection connection;
    private final String[] currencyTypes;

    public PNLPanel(Connection connection, String[] currencyTypes){
        this.connection = connection;
        this.currencyTypes = currencyTypes;

        createPNLTab();
        fillPNLTab();
    }

    private void createPNLTab() {
        setLayout(new BorderLayout());
    }

    private void fillPNLTab() {
        String[] columnNames = {"Close",
                "Date",
                "Rate",
                "Foreign Currency Details",
                "Total USD Value in FX",
                "PnL for all Currencies",
                "Remaining USD Value from Original Investment"
        };
        int rows = 5;
        DefaultTableModel model = new DefaultTableModel(rows, columnNames.length);
        model.setColumnIdentifiers(columnNames);

        JTable table = new JTable(model);
        add(new JScrollPane(table));
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(25, 50, 300, 300);
        add(sp);
    }

}
