import frame.StoredProcs;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class PositionAtTimeTest
{
    StoredProcs sp = Mockito.mock(StoredProcs.class);
    String[] currencies = {"ILS", "EUR", "JPY", "GBP", "AUD", "CAD", "CHF", "CNH", "HKD"};
    PositionAtTime posCalc = new PositionAtTime(sp, currencies, 1000.00);
    LocalDate date = LocalDate.now();

    @Test
    public void getPositionAtTime_basic() throws SQLException
    {
        // given
        when(sp.getAllTransactions()).thenReturn(null);
        HashMap<String, Double> expected = new HashMap<>();
        expected.put("USD", 1000.00);
        expected.put("ILS", 0.0);
        expected.put("EUR", 0.0);
        expected.put("JPY", 0.0);
        expected.put("GBP", 0.0);
        expected.put("AUD", 0.0);
        expected.put("CAD", 0.0);
        expected.put("CHF", 0.0);
        expected.put("CNH", 0.0);
        expected.put("HKD", 0.0);

        // when
        HashMap<String, Double> positions = posCalc.getPositionAtTime(date);

        // then
        assertEquals(expected, positions);
    }

    @Test
    public void getPositionAtTime_spots() throws SQLException
    {
        // given
        // form: date, vendor, quantity, type, currency, rate, maturity
        ArrayList<String[]> transactions = new ArrayList<>();
        transactions.add(new String[] {"2022-01-01","","300","spot","ILS","3.6",""});
        when(sp.getAllTransactions()).thenReturn(transactions);
        HashMap<String, Double> expected = new HashMap<>();
        expected.put("USD", 916.67);
        expected.put("ILS", 300.0);
        expected.put("EUR", 0.0);
        expected.put("JPY", 0.0);
        expected.put("GBP", 0.0);
        expected.put("AUD", 0.0);
        expected.put("CAD", 0.0);
        expected.put("CHF", 0.0);
        expected.put("CNH", 0.0);
        expected.put("HKD", 0.0);

        // when
        HashMap<String, Double> positions = posCalc.getPositionAtTime(date);

        // then
        assertEquals(expected, positions);
    }

    @Test
    public void getPositionAtTime_futures() throws SQLException
    {
        // given
        // form: date, vendor, quantity, type, currency, rate, maturity
        ArrayList<String[]> transactions = new ArrayList<>();
        transactions.add(new String[] {"2022-01-01","","300","future","ILS","3.6","2022-01-02"});
        transactions.add(new String[] {"2022-01-01","","300","future","ILS","3.6","2026-01-01"});
        when(sp.getAllTransactions()).thenReturn(transactions);
        HashMap<String, Double> expected = new HashMap<>();
        expected.put("USD", 916.67);
        expected.put("ILS", 300.0);
        expected.put("EUR", 0.0);
        expected.put("JPY", 0.0);
        expected.put("GBP", 0.0);
        expected.put("AUD", 0.0);
        expected.put("CAD", 0.0);
        expected.put("CHF", 0.0);
        expected.put("CNH", 0.0);
        expected.put("HKD", 0.0);

        // when
        HashMap<String, Double> positions = posCalc.getPositionAtTime(date);

        // then
        assertEquals(expected, positions);
    }

}