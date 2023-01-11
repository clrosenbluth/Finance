package Tools;

import api_connection.FXRateService;
import api_connection.FXRateServiceFactory;
import api_data.RealTimeFXData;
import frame.StoredProcs;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;

public class PositionAndPresentValueAtTime
{
    private FXRateService service;
    private StoredProcs sp;
    private String[] currencies;
    private double initialUSD;
    private LocalDate date;
    private HashMap<String, Double> position;
    private Double presentValue;
    private ArrayList<String[]> records;

    public PositionAndPresentValueAtTime(LocalDate date,
                                         StoredProcs sp,
                                         String[] currencies,
                                         double initialUSD)
            throws SQLException
    {
        this.service = new FXRateServiceFactory().getInstance();

        this.sp = sp;
        this.currencies = currencies;
        this.initialUSD = initialUSD;

        position = new HashMap<>();
        position.put("USD", this.initialUSD); // adding separately because not included in currency list
        for (String curr : this.currencies)
        {
            position.put(curr, 0.0);
        }

        records = new ArrayList<>();

        setDate(date);
    }

    public void setDate(LocalDate date) throws SQLException
    {
        this.date = date;
        records = sp.getAllTransactions();
        updatePosition(records);
        updatePresentValue(records);
    }

    public HashMap<String, Double> getPosition()
    {
        return position;
    }

    public Double getPresentValue()
    {
        updatePresentValue(records);
        return presentValue;
    }

    private void updatePosition(ArrayList<String[]> records)
    {
        if (records != null)
        {
            // form: date, vendor, quantity, type, currency, rate, maturity
            // quantity: amount in FX
            // rate: FX per dollar
            for (String[] record : records)
            {
                String t_date = record[0];
                if (LocalDate.parse(t_date).isAfter(date))
                {
                    continue;
                }

                // otherwise:
                String type = record[3];
                String maturityD = record[6];
                LocalDate maturityLD = maturityD == null || maturityD.equals("") ?
                        null :
                        LocalDate.parse(maturityD);

                boolean isSpot = type.equals(Constants.SPOT.label);
                boolean isMaturedFuture = type.equals(Constants.FUTURE.label)
                        && maturityD != null        // added to avoid NPE
                        && maturityLD.isBefore(date);
                if (isSpot || isMaturedFuture)
                {
                    Double quantity = Double.parseDouble(record[2]);
                    String currency = record[4];
                    Double rate = Double.parseDouble(record[5]);

                    // remove (or add) the number of dollars
                    double base = position.get("USD");
                    double fxInDollars = quantity / rate;
                    // make sure it has 2 decimal places
                    fxInDollars = fxInDollars*100;
                    fxInDollars = (double)((int) fxInDollars);
                    fxInDollars = fxInDollars /100;

                    double newBase = base - fxInDollars;
                    position.put("USD", newBase);

                    // add (or remove) the number of FX units
                    Double fx = position.get(currency);
                    Double newFX = fx + quantity;
                    position.put(currency, newFX);
                }
                //todo: forwards: nothing is exchanged until the maturity date
                // value accumulates because the promise can be sold
            }
        }
    }

    private void updatePresentValue(ArrayList<String[]> records)
    {
        double value = 0.0;
        // get value from position
        value += position.get("USD");

        for (String curr : currencies)
        {
            double pos = position.get(curr);
            RealTimeFXData fxData = new RealTimeFXData(service);
            double rate = fxData.getRealTimeFXRate(curr);
            value += pos / rate;
        }

        // get value from unmatured futures
        if (records != null)
        {
            // form: date, vendor, quantity, type, currency, rate, maturity
            // quantity: amount in FX
            // rate: FX per dollar
            for (String[] record : records)
            {
                String t_date_string = record[0];
                LocalDate t_date = LocalDate.parse(t_date_string);
                String type = record[3];
                String maturityD = record[6];
                LocalDate maturityLD = maturityD == null || maturityD.equals("") ?
                        null :
                        LocalDate.parse(maturityD);

                boolean isPendingPurchasedFuture = type.equals("future")
                        && maturityD != null
                        && maturityLD.isAfter(date)
                        && t_date.isAfter(date);
                if (isPendingPurchasedFuture)
                {
                    Double quantity = Double.parseDouble(record[2]);
                    String currency = record[4];
                    // todo: confirm formula
                    // value = percentage of final amount, where percentage is percent of time passed
                    double fullTime = t_date.until(maturityLD, ChronoUnit.DAYS);
                    double passedTime = t_date.until(date, ChronoUnit.DAYS);
                    double elapsedPercentage = passedTime/fullTime;
                    double valueAccruedInFX = elapsedPercentage * quantity;

                    RealTimeFXData fxData = new RealTimeFXData(service);
                    double rate = fxData.getRealTimeFXRate(currency);

                    value += valueAccruedInFX / rate;
                }
            }
        }
        presentValue = value;
    }
}
