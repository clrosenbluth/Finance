import frame.StoredProcs;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class PositionAtTime
{
    StoredProcs sp;
    String[] currencies;
    double initialUSD;

    public PositionAtTime(StoredProcs sp, String[] currencies, double initialUSD)
    {
        this.sp = sp;
        this.currencies = currencies;
        this.initialUSD = initialUSD;
    }

    // todo: return map of currency type and amount current held in that currency
    public HashMap<String, Double> getPositionAtTime(LocalDate date) throws SQLException
    {
        HashMap<String, Double> positions = new HashMap<>();
        positions.put("USD", initialUSD); // adding separately because not included in currency list
        for (String curr : currencies)
        {
            positions.put(curr, 0.0);
        }

        ArrayList<String[]> records = sp.getAllTransactions();
        // form: date, vendor, quantity, type, currency, rate, maturity
        // quantity: amount in FX
        // rate: FX per dollar
        if (records != null)
        {
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

                boolean isSpot = type.equals("spot");
                boolean isGoodFuture = type.equals("future")
                        && maturityD != null
                        && maturityLD.isAfter(date);
                if (isSpot || isGoodFuture)
                {
                    Double quantity = Double.parseDouble(record[2]);
                    String currency = record[4];
                    Double rate = Double.parseDouble(record[5]);

                    // remove (or add) the number of dollars
                    double base = positions.get("USD");
                    double fxInDollars = quantity / rate;
                    // make sure it has 2 decimal places
                    fxInDollars = fxInDollars*100;
                    fxInDollars = (double)((int) fxInDollars);
                    fxInDollars = fxInDollars /100;

                    double newBase = base - fxInDollars;
                    positions.put("USD", newBase);

                    // add (or remove) the number of FX units
                    Double fx = positions.get(currency);
                    Double newFX = fx + quantity;
                    positions.put(currency, newFX);
                }
                //todo: forwards: nothing is exchanged until the maturity date
                // value accumulates because the promise can be sold
            }
        }
        return positions;
    }
}
