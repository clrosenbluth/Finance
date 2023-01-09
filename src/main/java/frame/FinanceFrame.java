package frame;

import frame.panels.PNLPanel;
import frame.panels.SandboxPanel;
import frame.panels.TransactionsPanel;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class FinanceFrame extends JFrame
{
    public JTabbedPane tabbedPane;
    public JPanel transactions;
    public JPanel pnl;
    public JPanel sandbox;
    String[] currencyTypes = {"ILS", "EUR", "JPY", "GBP", "AUD", "CAD", "CHF", "CNH", "HKD"};
    Connection connection;
    StoredProcs storedProcedures;

    public FinanceFrame()
    {
        setTitle("FX Position Tracker");
        setSize(1000, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1,1));

        try
        {
            //  todo: insert password, but do not push
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/finance",
                    "root",
                    "");
            storedProcedures = new StoredProcs(connection);
        } catch (Exception e)
        {
            e.printStackTrace();
        }


        addTabbedPane();

        createTransactionsTab();
        createPnlTab();
        createSandboxTab();
    }

    private void addTabbedPane()
    {
        tabbedPane = new JTabbedPane();
        add(tabbedPane);
    }

    private void createTransactionsTab()
    {
        transactions = new TransactionsPanel(currencyTypes, storedProcedures);
        tabbedPane.addTab("Transactions", transactions);
    }
    private void createPnlTab()
    {
        pnl = new PNLPanel(currencyTypes, storedProcedures);
        tabbedPane.addTab("PnL", pnl);
    }

    private void createSandboxTab()
    {
        sandbox = new SandboxPanel(currencyTypes, storedProcedures);
        tabbedPane.addTab("Sandbox", sandbox);
    }

    public static void main(String[] args)
    {
        FinanceFrame frame = new FinanceFrame();
        frame.setVisible(true);
    }
}
