package api_data;

import api_connection.FXRateService;
import api_connection.RealTimeFXRate;
import retrofit2.Response;

import java.io.IOException;

public class RealTimeFXData {
    private RealTimeFXRate realTimeFXRate = null;

    public RealTimeFXData(FXRateService fxRateService) {
        try {
            Response<RealTimeFXRate> realTimeFXRateResponse = fxRateService.getRealTimeFXRate().execute();
            realTimeFXRate = realTimeFXRateResponse.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param ticker of foreign currency - formatted as "ABC"
     * @return realtime currency exchange rate
     */
    public double getRealTimeFXRate(String ticker) {
        return realTimeFXRate.rates.get(ticker);
    }

}
