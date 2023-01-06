package api_connection.daily_service;

import org.junit.jupiter.api.Test;
import retrofit2.Response;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class DailyFXServiceTest {
    DailyFXServiceFactory factory = new DailyFXServiceFactory();
    DailyFXService service = factory.getInstance();

    @Test
    void getInstanceofDailyService() {
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
        assertEquals(100, timeSeries.timeSeriesFX.size());
    }
}