import frame.StoredProcs;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class PositionAtTime
{
    StoredProcs sp;

    public PositionAtTime(StoredProcs sp)
    {
        this.sp = sp;
    }

    public Double getPositionAtTime(LocalDate date) throws SQLException
    {
        double sum = 0.0;
        ArrayList<String[]> records = sp.getAllTransactions();
        // form: date, vendor, quantity, type, currency, rate, maturity
        for (String[] record : records)
        {
            String t_date = record[0];
            if (LocalDate.parse(t_date).isAfter(date))
            {
                continue;
            }

            // otherwise:
            Double quantity = Double.parseDouble(record[2]);
            Double rate = Double.parseDouble(record[5]);
            sum += (quantity * rate);
        }
        return sum;
    }
}
