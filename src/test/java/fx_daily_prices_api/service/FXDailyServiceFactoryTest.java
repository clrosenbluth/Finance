package fx_daily_prices_api.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FXDailyServiceFactoryTest {

    @Test
    void getInstance() {
        //given
        FXDailyServiceFactory factory = new FXDailyServiceFactory();
        FXDailyService service = factory.getInstance();

        //when
        FXData fxData = service.getFXData("EUR")
                .blockingGet();

        //then
        assertNotEquals(0,fxData.close);
        assertNotEquals(0, fxData.open);
    }
}