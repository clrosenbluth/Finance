package api_data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FXDataTest {
    FXData fxData = new FXData("JPY");

    @Test
    void getClose() {
        //given

        //when
        double close = fxData.getClose("2022-12-30");

        //then
        assertTrue(close > 0);
    }

    @Test
    void getRealTimeFXRate() {
        //given

        //when
        double rate = fxData.getRealTimeFXRate();

        //then
        assertEquals(133.62100000, rate);

    }
}