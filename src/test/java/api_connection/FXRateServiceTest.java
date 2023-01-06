package api_connection;

import org.junit.jupiter.api.Test;
import retrofit2.Response;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FXRateServiceTest {
    FXRateServiceFactory factory = new FXRateServiceFactory();
    FXRateService service = factory.getInstance();

    @Test
    void getDailyFXData() {
        //given

        //when
        TimeSeries timeSeries = null;
        try{
            Response<TimeSeries> response = service.getDailyFXData("EUR").execute();
            timeSeries = response.body();
        } catch (IOException e){
            e.printStackTrace();
        }

        //then
        assertNotNull(timeSeries);
        assertTrue(timeSeries.timeSeriesFX.size() > 2000);
    }

    @Test
    void getRealTimeFXRate() {
        //given

        //when
        RealTimeFXRate realTimeFXRate= null;
        try{
            Response<RealTimeFXRate> response = service.getRealTimeFXRate("EUR").execute();
            realTimeFXRate = response.body();
        } catch (IOException e){
            e.printStackTrace();
        }

        //then
        assertNotNull(realTimeFXRate);
        assertEquals(9, realTimeFXRate.realTimeExchangeRate.size());

    }
}