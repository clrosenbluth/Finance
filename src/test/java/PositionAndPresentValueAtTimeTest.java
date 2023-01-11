import Tools.PositionAndPresentValueAtTime;
import api_connection.FXRateService;
import api_connection.FXRateServiceFactory;
import api_data.DailyCloseData;
import api_data.RealTimeFXData;
import frame.StoredProcs;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class PositionAndPresentValueAtTimeTest
{
    StoredProcs sp;
    RealTimeFXData realTimeFXData;
    DailyCloseData dailyCloseData;
    PositionAndPresentValueAtTime calc;
    HashMap<String, Double> expectedPosition;
    HashMap<String, Double> actualPosition;
    Double initialUSD = 1000.00;
    Double expectedPresentValue;
    Double actualPresentValue;
    RealTimeFXData fxData;
    double rate;

    // todo: fix tests
    PositionAndPresentValueAtTimeTest() throws SQLException
    {
        sp = Mockito.mock(StoredProcs.class);
        ArrayList<String[]> transactions = new ArrayList<>();
        transactions.add(new String[] {"2016-01-01","bank","300","Spot","ILS","3",""});
        transactions.add(new String[] {"2016-01-03","bank","300","Future","ILS","3","2017-01-03"});
        transactions.add(new String[] {"2016-01-05","bank","300","Future","ILS","3","2018-01-05"});
        when(sp.getAllTransactions()).thenReturn(transactions);

        FXRateService service = new FXRateServiceFactory().getInstance();
        fxData = new RealTimeFXData(service);
        rate = fxData.getRealTimeFXRate("ILS");

        realTimeFXData = Mockito.mock(RealTimeFXData.class);
        dailyCloseData = Mockito.mock(DailyCloseData.class);
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
    }

    @Test
    public void getInfoAfterTransaction1() throws SQLException
    {
        // given
        calc.setDate(LocalDate.parse("2016-01-02"));
        expectedPresentValue = (initialUSD - 100.00) + (300 / rate);

        expectedPosition.put("USD", initialUSD - 100.00);
        expectedPosition.put("ILS", 300.00);

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
        calc.setDate(LocalDate.parse("2016-01-04"));
        // transaction 1 + value of transaction 2
        expectedPresentValue = (initialUSD - 100.00) + (300 / rate) + ((300/365) / rate);

        expectedPosition.put("USD", initialUSD - 100.00);
        expectedPosition.put("ILS", 300.00);

        // when
        actualPosition = calc.getPosition();
        actualPresentValue = calc.getPresentValue();

        // then
        assertEquals(expectedPosition, actualPosition);
        assertEquals(expectedPresentValue, actualPresentValue);
    }

    @Test
    public void getInfoAfterTransaction2MaturedAndTransaction3Made() throws SQLException
    {
        // given
        calc.setDate(LocalDate.parse("2017-01-04"));
        // transaction 1 + transaction 2 + value of transaction 3
        expectedPresentValue = (initialUSD - 100.00 - 100.0) + ((300 + 300) / rate) +
                ((364/(365 * 2)) / rate);

        expectedPosition.put("USD", initialUSD - 100.00 - 100.00);
        expectedPosition.put("ILS", 300.00 + 300.00);

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
        calc.setDate(LocalDate.parse("2018-01-06"));
        // transaction 1 + transaction 2 + transaction 3
        expectedPresentValue = (initialUSD - 100.00 - 100.0 - 100) + ((300 + 300 + 300) / rate);

        expectedPosition.put("USD", initialUSD - 100.00 - 100.00 - 100.00);
        expectedPosition.put("ILS", 300.00 + 300.00 + 300.00);

        // when
        actualPosition = calc.getPosition();
        actualPresentValue = calc.getPresentValue();

        // then
        assertEquals(expectedPosition, actualPosition);
        assertEquals(expectedPresentValue, actualPresentValue);
    }

}