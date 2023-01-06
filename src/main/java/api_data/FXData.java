package api_data;

import api_connection.daily_service.DailyFXService;
import api_connection.daily_service.DailyFXServiceFactory;
import api_connection.daily_service.TimeSeries;
import api_connection.exchange_rates_service.RealTimeFXRate;
import api_connection.exchange_rates_service.ExchangeFXRatesService;
import api_connection.exchange_rates_service.ExchangeFXRatesFactory;
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

        DailyFXServiceFactory dailyFXServiceFactory = new DailyFXServiceFactory();
        DailyFXService dailyFXService = dailyFXServiceFactory.getInstance();

        ExchangeFXRatesFactory exchangeFXRatesFactory = new ExchangeFXRatesFactory();
        ExchangeFXRatesService exchangeFXRatesService = exchangeFXRatesFactory.getInstance();

        getTimeSeries(dailyFXService);
        getRealTimeFXRate(exchangeFXRatesService);
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
     * returns realtime currency exchange rate
     */
    public double getRealTimeFXRate() {
        String rate = realTimeFXRate.realTimeExchangeRate.get("5. Exchange Rate");
        return Double.parseDouble(rate);
    }

    private void getTimeSeries(DailyFXService service) {
        try {
            Response<TimeSeries> response = service.getDailyFXData(ticker).execute();
            timeSeries = response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getRealTimeFXRate(ExchangeFXRatesService service) {
        try{
            Response<RealTimeFXRate> response = service.getRealTimeFXRate(ticker).execute();
            realTimeFXRate = response.body();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
