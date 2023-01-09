package presenter;

import Tools.Constants;
import Tools.RateCalculator;
import frame.StoredProcs;
import frame.panels.TransactionsPanel;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class TransactionPresenter
{
    TransactionsPanel panel;
    RateCalculator calculator;
    StoredProcs storedProcedures;

    public TransactionPresenter(TransactionsPanel panel, StoredProcs storedProcedures) {
        this.panel = panel;
        calculator = new RateCalculator();
        this.storedProcedures = storedProcedures;
    }

    public double getNPV()
    {
        // todo
        return 0.0;
    }

    public void tryToAdd()
    {
        if (fieldsAreValid() && hasEnoughMoney())
        {
            Integer totalDays = Math.toIntExact(panel.getType().equals(Constants.SPOT.label) ?
                    0 :
                    LocalDate.parse(panel.getTransactionDate()).until(
                            LocalDate.parse(panel.getMaturityDate()),
                            ChronoUnit.DAYS
                    )
            );

            String impliedRate = panel.getType().equals(Constants.SPOT.label) ?
                    "N/A" :
                    calculator.getContinuousRate(Float.parseFloat(panel.getRate()), totalDays).toString();

            String maturityDate = panel.getType().equals(Constants.SPOT.label) ?
                    "N/A" :
                    panel.getMaturityDate();

            panel.addRowToModel(
                    panel.getTransactionDate(),
                    panel.getVendor(),
                    panel.getType(),
                    panel.getQuant(),
                    panel.getFX(),
                    panel.getRate(),
                    maturityDate,
                    impliedRate);

            panel.clearFields();

            Date insertMaturity = maturityDate.equals("N/A") ?
                    null :
                    Date.valueOf(maturityDate);

            try {
                storedProcedures.insertTransaction(
                        insertMaturity,
                        panel.getVendor(),
                        Float.parseFloat(panel.getQuant()),
                        panel.getType(),
                        panel.getFX(),
                        Float.parseFloat(panel.getRate()),
                        insertMaturity);
            } catch (Exception e) {
                panel.sendErrorMessage("Unable to add transaction to database");
            }
        }
        else {
            panel.sendErrorMessage("Please ensure all fields are valid.");
        }
    }

    private boolean fieldsAreValid()
    {
        boolean validQuant = true;
        try {
            double quant = Double.parseDouble(panel.getQuant());
        } catch (Exception e) {
            validQuant = false;
        }

        boolean validRate = true;
        try {
            double rate = Double.parseDouble(panel.getRate());
        } catch (Exception e) {
            validRate = false;
        }

        boolean validMaturityDate = panel.getMaturityDate() != null;

        return validQuant
                && validRate
                && validMaturityDate;
    }

    private boolean hasEnoughMoney()
    {
        // todo: get this from quant and api and positions
        return true;
    }

    public void fillTransactionTableFromDatabase()
    {
        ArrayList<String[]> transactions;
        try
        {
            transactions = storedProcedures.getAllTransactions();
            if (transactions != null)
            {
                for (String[] transaction : transactions)
                {
                    panel.addRowToModel(
                            transaction[0],
                            transaction[1],
                            transaction[2],
                            transaction[3],
                            transaction[4],
                            transaction[5],
                            transaction[6],
                            transaction[7]
                    );
                }
            }

        } catch (Exception e)
        {
            panel.sendErrorMessage("Unable to load transactions from database.");
        }

    }
}
