package api_connection;

import org.junit.jupiter.api.Test;
import retrofit2.Response;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FXRatesServiceTest {
    FXRatesServiceFactory factory = new FXRatesServiceFactory();
    FXRatesService service = factory.getInstance();

    @Test
    void getInstanceofDailyService() {
        //given

        //when
        TimeSeries timeSeries = null;
        try{
            Response<TimeSeries> response = service.getFXData("EUR").execute();
            timeSeries = response.body();
        } catch (IOException e){
            e.printStackTrace();
        }

        //then
        assertNotNull(timeSeries);
        assertEquals(101, timeSeries.timeSeriesFX.size());
    }

    @Test
    void getInstanceofRealtimeFXService() {
        //given

        //when
        RealTimeFXRate realTimeFXRate= null;
        try{
            Response<RealTimeFXRate> response = service.getRealTimeFXRate("JPY").execute();
            realTimeFXRate = response.body();
        } catch (IOException e){
            e.printStackTrace();
        }

        //then
        assertNotNull(realTimeFXRate);

    }
}