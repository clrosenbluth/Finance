package api_connection.exchange_rates_service;

import org.junit.jupiter.api.Test;
import retrofit2.Response;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ExchangeFXRatesServiceTest {
    ExchangeFXRatesFactory factory = new ExchangeFXRatesFactory();
    ExchangeFXRatesService service = factory.getInstance();

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