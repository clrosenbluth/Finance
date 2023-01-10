package presenter;

import Tools.Constants;
import Tools.RateCalculator;
import frame.panels.SandboxPanel;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class SandboxPresenter
{
    private SandboxPanel panel;
    private RateCalculator calculator;

    public SandboxPresenter(SandboxPanel panel)
    {
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
        } else {
            panel.sendErrorMessage();
        }
    }

    private boolean fieldsAreValid()
    {
        boolean validTransactionDate = panel.getTransactionDate() != null;

        boolean validForwardQuant = true;
        try
        {
            double forwardQuant = Double.parseDouble(panel.getQuant());
        } catch (Exception e)
        {
            validForwardQuant = false;
        }

        boolean validRate = true;
        try
        {
            double rate = Double.parseDouble(panel.getRate());
        } catch (Exception e)
        {
            validRate = false;
        }

        boolean validMaturityDate = panel.getMaturityDate() != null;
        if (validTransactionDate && validMaturityDate)
        {
            validMaturityDate = LocalDate.parse(panel.getTransactionDate())
                    .isBefore(LocalDate.parse(panel.getMaturityDate()));
        }

        return validTransactionDate
                && validForwardQuant
                && validRate
                && validMaturityDate;
    }

    private boolean hasEnoughMoney()
    {
        // TODO: implement
        return true;
    }


}
