package frame.panels;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PNLPanel extends JPanel{
    private final String[] currencyTypes;

    private double overallPNL = 0.0; //since initial investment or overall pnl within given time frame
    private double investment = 10000; //holds value of what is remaining from original investment

    public PNLPanel(String[] currencyTypes){
        this.currencyTypes = currencyTypes;

        createPNLTab();
        fillPNLTab();
    }

    private void createPNLTab() {
        setLayout(new BorderLayout());
    }

    private void fillPNLTab() {
        String[] columnNames = {
                "Date",
                "Total FX Position",
                "Remaining USD Value from Original Investment",
                "PnL",

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
