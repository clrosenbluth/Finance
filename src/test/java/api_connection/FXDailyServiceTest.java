package api_connection;

import org.junit.jupiter.api.Test;
import retrofit2.Response;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FXDailyServiceTest {

    @Test
    void getInstance() {
        //given
        FXDailyServiceFactory factory = new FXDailyServiceFactory();
        FXDailyService service = factory.getInstance();

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
        assertEquals(100, timeSeries.timeSeriesFX.size());
    }
}