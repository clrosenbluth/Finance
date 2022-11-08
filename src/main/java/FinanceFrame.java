import javax.swing.*;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;

public class FinanceFrame extends JFrame
{
    public JTabbedPane tabbedPane;
    public JPanel transactions;
    public JPanel pnl;
    public JPanel sandbox;
    String[] currencyTypes = {"USD", "ILS", "EUR", "JPY", "GBP", "AUD", "CAD", "CHF", "CNH", "HKD"};

    public FinanceFrame()
    {
        setTitle("Finance Calculator");
        setSize(1000, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1,1));

        addTabbedPane();

        createTransactionsTab();
        fillTransactionsTab();

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
        transactions = new JPanel();
        transactions.setLayout(new BoxLayout(transactions, BoxLayout.PAGE_AXIS));
        tabbedPane.addTab("Transactions", transactions);
    }

    private void fillTransactionsTab()
    {
        JLabel reportLabel = new JLabel();
        reportLabel.setText("Overall position: 10000 USD");
        transactions.add(reportLabel);

        addNewTransactionPanel();

        addTransactionsTable();
    }

    private void addNewTransactionPanel()
    {
        JPanel newTransactionPanel = new JPanel(new FlowLayout());

        JPanel transactionDatePanel = new JPanel();
        transactionDatePanel.setLayout(new BoxLayout(transactionDatePanel, BoxLayout.PAGE_AXIS));
        JLabel transactionDate = new JLabel("Transaction Date");
        JTextField transactionDateInput = new JTextField();
        transactionDatePanel.add(transactionDate);
        transactionDatePanel.add(transactionDateInput);

        JPanel vendorPanel = new JPanel();
        vendorPanel.setLayout(new BoxLayout(vendorPanel, BoxLayout.PAGE_AXIS));
        JLabel vendor = new JLabel("Vendor");
        JTextField vendorInput = new JTextField();
        vendorPanel.add(vendor);
        vendorPanel.add(vendorInput);

        JPanel transactionTypePanel = new JPanel();
        transactionTypePanel.setLayout(new BoxLayout(transactionTypePanel, BoxLayout.PAGE_AXIS));
        JLabel transactionType = new JLabel("Transaction Type");
        String[] transactionTypes = {"Spot", "Future"};
        JComboBox<String> transactionTypeInput = new JComboBox<>(transactionTypes);
        transactionTypeInput.setSelectedIndex((0));
        transactionTypePanel.add(transactionType);
        transactionTypePanel.add(transactionTypeInput);

        JPanel forwardQuantityPanel = new JPanel();
        forwardQuantityPanel.setLayout(new BoxLayout(forwardQuantityPanel, BoxLayout.PAGE_AXIS));
        JLabel forwardQuantity = new JLabel("Forward Quantity");
        JTextField forwardQuantityInput = new JTextField();
        forwardQuantityPanel.add(forwardQuantity);
        forwardQuantityPanel.add(forwardQuantityInput);

        JPanel baseCurrencyPanel = new JPanel();
        baseCurrencyPanel.setLayout(new BoxLayout(baseCurrencyPanel, BoxLayout.PAGE_AXIS));
        JLabel baseCurrency = new JLabel("Base Currency");
        JComboBox<String> baseCurrencyInput = new JComboBox<>(currencyTypes);
        baseCurrencyPanel.add(baseCurrency);
        baseCurrencyPanel.add(baseCurrencyInput);

        JPanel foreignCurrencyPanel = new JPanel();
        foreignCurrencyPanel.setLayout(new BoxLayout(foreignCurrencyPanel, BoxLayout.PAGE_AXIS));
        JLabel foreignCurrency = new JLabel("Foreign Currency");
        JComboBox<String> foreignCurrencyInput = new JComboBox<>(currencyTypes);
        foreignCurrencyPanel.add(foreignCurrency);
        foreignCurrencyPanel.add(foreignCurrencyInput);

        JPanel ratePanel = new JPanel();
        ratePanel.setLayout(new BoxLayout(ratePanel, BoxLayout.PAGE_AXIS));
        JLabel rate = new JLabel("Rate");
        JTextField rateInput = new JTextField();
        ratePanel.add(rate);
        ratePanel.add(rateInput);

        JPanel maturityDatePanel = new JPanel();
        maturityDatePanel.setLayout(new BoxLayout(maturityDatePanel, BoxLayout.PAGE_AXIS));
        JLabel maturityDate = new JLabel("Maturity Date");
        JTextField maturityDateInput = new JTextField();
        maturityDatePanel.add(maturityDate);
        maturityDatePanel.add(maturityDateInput);

        JPanel forwardRatePanel = new JPanel();
        forwardRatePanel.setLayout(new BoxLayout(forwardRatePanel, BoxLayout.PAGE_AXIS));
        JLabel forwardRate = new JLabel("Forward Rate");
        JTextField forwardRateInput = new JTextField();
        forwardRatePanel.add(forwardRate);
        forwardRatePanel.add(forwardRateInput);

        newTransactionPanel.add(transactionDatePanel);
        newTransactionPanel.add(vendorPanel);
        newTransactionPanel.add(transactionTypePanel);
        newTransactionPanel.add(forwardQuantityPanel);
        newTransactionPanel.add(baseCurrencyPanel);
        newTransactionPanel.add(foreignCurrencyPanel);
        newTransactionPanel.add(ratePanel);
        newTransactionPanel.add(maturityDatePanel);
        newTransactionPanel.add(forwardRatePanel);

        newTransactionPanel.add(new JButton("Add Transaction"));

        transactions.add(newTransactionPanel);
    }

    private void addTransactionsTable()
    {
        String[] columnNames = {"Transaction Date",
                                "Vendor",
                                "Type",
                                "Quantity",
                                "Base Currency",
                                "Foreign Currency",
                                "Rate",
                                "Maturity Date",
                                "Forward Rate",
                                "Implied Risk-Free Rate"};

        int numRows = 5;
        DefaultTableModel model = new DefaultTableModel(numRows, columnNames.length);
        model.setColumnIdentifiers(columnNames);

        String[][] data = {{"a","a","a","a","a","a","a","a","a","a"},{"s","s","s","s","s","s","s","s","s","s"}};

        JTable table = new JTable(model);
//        JTable table = new JTable(data, columnNames);
        table.setBackground(Color.green);
        JScrollPane sp = new JScrollPane(table);
        sp.setBackground(Color.magenta);
        sp.setBounds(25, 50, 300, 300);
        transactions.add(sp);
    }

    private void createPnlTab()
    {
        pnl = new JPanel(new BorderLayout());
        tabbedPane.addTab("PnL", pnl);
    }

    private void createSandboxTab()
    {
        sandbox = new JPanel(new BorderLayout());
        tabbedPane.addTab("Sandbox", sandbox);
    }

    public static void main(String[] args)
    {
        FinanceFrame frame = new FinanceFrame();
        frame.setVisible(true);
    }
}
