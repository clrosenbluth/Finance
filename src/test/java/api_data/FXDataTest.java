package api_data;

import api_connection.FXRateService;
import api_connection.FXRateServiceFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FXDataTest {
    FXRateServiceFactory factory = new FXRateServiceFactory();
    FXRateService service = factory.getInstance();

    @Test
    void getClose() {
        //given
        DailyCloseData dailyCloseData = new DailyCloseData(service, "2022-12-30", "2023-01-05");

        //when
        double close = dailyCloseData.getClose("2022-12-30", "JPY");

        //then
        assertTrue(close > 130 && close < 140);
    }

    @Test
    void getRealTimeFXRate() {
        //given
        RealTimeFXData fxData = new RealTimeFXData(service);

        //when
        double realTimeRate = fxData.getRealTimeFXRate("JPY");

        //then
        assertTrue(realTimeRate > 130 && realTimeRate < 140);

    }
}