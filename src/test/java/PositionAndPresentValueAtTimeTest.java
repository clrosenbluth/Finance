import Tools.PositionAndPresentValueAtTime;
import api_data.FXData;
import frame.StoredProcs;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class PositionAndPresentValueAtTimeTest
{
    StoredProcs sp;
    FXData fxData;
    PositionAndPresentValueAtTime calc;
    HashMap<String, Double> expectedPosition;
    HashMap<String, Double> actualPosition;
    Double initialUSD = 1000.00;
    Double expectedPresentValue;
    Double actualPresentValue;

    // todo: fix tests
    PositionAndPresentValueAtTimeTest() throws SQLException
    {
        sp = Mockito.mock(StoredProcs.class);
        fxData = Mockito.mock(FXData.class);
        String[] currencies = {"ILS", "EUR", "JPY", "GBP", "AUD", "CAD", "CHF", "CNH", "HKD"};
        calc = new PositionAndPresentValueAtTime(
                LocalDate.now(),
                sp,
                currencies,
                initialUSD
        );

        expectedPosition = new HashMap<>();
        expectedPosition.put("USD", initialUSD);
        expectedPosition.put("ILS", 0.0);
        expectedPosition.put("EUR", 0.0);
        expectedPosition.put("JPY", 0.0);
        expectedPosition.put("GBP", 0.0);
        expectedPosition.put("AUD", 0.0);
        expectedPosition.put("CAD", 0.0);
        expectedPosition.put("CHF", 0.0);
        expectedPosition.put("CNH", 0.0);
        expectedPosition.put("HKD", 0.0);
        actualPosition = new HashMap<>();

        ArrayList<String[]> transactions = new ArrayList<>();
        transactions.add(new String[] {"2016-01-01","bank","300","Spot","ILS","3",""});
        transactions.add(new String[] {"2016-01-03","bank","300","Future","ILS","3","2017-01-03"});
        transactions.add(new String[] {"2016-01-05","bank","300","Future","ILS","3","2018-01-05"});
        when(sp.getAllTransactions()).thenReturn(transactions);
    }

    @Test
    public void getInfoAfterTransaction1() throws SQLException
    {
        // given
        when(fxData.getClose("2016-01-02")).thenReturn(3.00);
        calc.setDate(LocalDate.of(2016, 1, 2));
        expectedPosition.put("USD", initialUSD - 100.00);
        expectedPosition.put("ILS", 300.00);
        expectedPresentValue = 1000.00;

        // when
        actualPosition = calc.getPosition();
        actualPresentValue = calc.getPresentValue();

        // then
        assertEquals(expectedPosition, actualPosition);
        assertEquals(expectedPresentValue, actualPresentValue);
    }

    @Test
    public void getInfoAfterTransaction2Made() throws SQLException
    {
        // given
        when(fxData.getClose("2016-01-05")).thenReturn(3.00);
        calc.setDate(LocalDate.of(2016, 1, 5));

        // when
        actualPosition = calc.getPosition();
        actualPresentValue = calc.getPresentValue();

        // then
        assertEquals(expectedPosition, actualPosition);
        assertEquals(expectedPresentValue, actualPresentValue);
    }

    @Test
    public void getInfoAfterTransaction2Matured() throws SQLException
    {
        // given
        when(fxData.getClose("2016-01-04")).thenReturn(3.00);
        calc.setDate(LocalDate.of(2017, 1, 4));

        // when
        actualPosition = calc.getPosition();
        actualPresentValue = calc.getPresentValue();

        // then
        assertEquals(expectedPosition, actualPosition);
        assertEquals(expectedPresentValue, actualPresentValue);
    }

    @Test
    public void getInfoAfterTransaction3Made() throws SQLException
    {
        // given
        when(fxData.getClose("2016-01-06")).thenReturn(3.00);
        calc.setDate(LocalDate.of(2016, 1, 6));

        // when
        actualPosition = calc.getPosition();
        actualPresentValue = calc.getPresentValue();

        // then
        assertEquals(expectedPosition, actualPosition);
        assertEquals(expectedPresentValue, actualPresentValue);
    }

    @Test
    public void getInfoAfterTransaction3Matured() throws SQLException
    {
        // given
        when(fxData.getClose("2018-01-06")).thenReturn(3.00);
        calc.setDate(LocalDate.of(2018, 1, 6));

        // when
        actualPosition = calc.getPosition();
        actualPresentValue = calc.getPresentValue();

        // then
        assertEquals(expectedPosition, actualPosition);
        assertEquals(expectedPresentValue, actualPresentValue);
    }

}