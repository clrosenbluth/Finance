package api_connection;

import org.junit.jupiter.api.Test;
import retrofit2.Response;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FXRateServiceTest {
    FXRateServiceFactory factory = new FXRateServiceFactory();
    FXRateService service = factory.getInstance();

    @Test
    void getDailyFXData() {
        //given

        //when
        DailyFXRate dailyFXRate = null;
        try{
            Response<DailyFXRate> response = service.getDailyFXData("2022-01-01", "2022-01-11").execute();
            dailyFXRate = response.body();
        } catch (IOException e){
            e.printStackTrace();
        }

        //then
        assertNotNull(dailyFXRate);
        assertEquals(11, dailyFXRate.rates.size());
    }

    @Test
    void getRealTimeFXRate() {
        //given

        //when
        RealTimeFXRate realTimeFXRate= null;
        try{
            Response<RealTimeFXRate> response = service.getRealTimeFXRate().execute();
            realTimeFXRate = response.body();
        } catch (IOException e){
            e.printStackTrace();
        }

        //then
        assertNotNull(realTimeFXRate);
        assertEquals(169, realTimeFXRate.rates.size());

    }
}