package presenter;

import Tools.Constants;
import Tools.RateCalculator;
import frame.panels.TransactionsPanel;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class TransactionPresenter
{
    TransactionsPanel panel;
    RateCalculator calculator;

    public TransactionPresenter(TransactionsPanel panel) {
        this.panel = panel;
        calculator = new RateCalculator();
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
            // todo: add to database
        }
        else
        {
            panel.sendErrorMessage();
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
}
