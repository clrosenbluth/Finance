package api_data;

import api_connection.FXRateService;
import api_connection.FXRateServiceFactory;
import api_connection.TimeSeries;
import api_connection.RealTimeFXRate;
import com.google.gson.internal.LinkedTreeMap;
import retrofit2.Response;

import java.io.IOException;

public class FXData {
    private final String ticker;
    private TimeSeries timeSeries = null;
    private RealTimeFXRate realTimeFXRate = null;


    /* ticker should be formatted as "ABC" */
    public FXData(String ticker) {
        this.ticker = ticker;

        FXRateServiceFactory fxRateServiceFactory = new FXRateServiceFactory();
        FXRateService fxRateService = fxRateServiceFactory.getInstance();

        try {
            Response<TimeSeries> timeSeriesResponse = fxRateService.getDailyFXData(ticker).execute();
            timeSeries = timeSeriesResponse.body();
            Response<RealTimeFXRate> realTimeFXRateResponse = fxRateService.getRealTimeFXRate(ticker).execute();
            realTimeFXRate = realTimeFXRateResponse.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param date format as "YYYY-MM-DD"
     * @return returns closing price in terms of foreign currency/USD
     *          ex: returns 3.50920 when buying/selling ILS
     */
    public Double getClose(String date) {
        LinkedTreeMap<String, String> dateMap = timeSeries.timeSeriesFX.get(date);
        return Double.parseDouble(dateMap.get("4. close"));
    }

    /**
     *
     * @return realtime currency exchange rate
     */
    public double getRealTimeFXRate() {
        String rate = realTimeFXRate.realTimeExchangeRate.get("5. Exchange Rate");
        return Double.parseDouble(rate);
    }

}
