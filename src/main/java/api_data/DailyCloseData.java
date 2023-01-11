package api_data;

import api_connection.DailyFXRate;
import api_connection.FXRateService;
import com.google.gson.internal.LinkedTreeMap;
import retrofit2.Response;

import java.io.IOException;

public class DailyCloseData {
    private DailyFXRate dailyFXRate = null;

    /**
     *
     * @param fxRateService
     * @param startDate format as "YYYY-MM-DD"
     * @param endDate format as "YYYY-MM-DD"
     */
    public DailyCloseData(FXRateService fxRateService, String startDate, String endDate) {
        try {
            Response<DailyFXRate> dailyFXRateResponse = fxRateService.getDailyFXData(startDate, endDate).execute();
            dailyFXRate = dailyFXRateResponse.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param date
     * @param ticker of foreign currency - formatted as "ABC"
     * @return returns closing price in terms of foreign currency/USD
     *          ex: returns 3.50920 when buying/selling ILS
     */
    public Double getClose(String date, String ticker) {
        LinkedTreeMap<String, Double> ratesForGivenRange = dailyFXRate.rates.get(date);
        return ratesForGivenRange.get(ticker);
    }
}
