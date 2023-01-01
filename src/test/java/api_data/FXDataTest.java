package api_data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FXDataTest {

    @Test
    void getClose() {
        //given
        FXData fxData = new FXData("EUR");

        //when
        double close = fxData.getClose("2022-12-30");

        //then
        assertTrue(close > 0);
    }
}