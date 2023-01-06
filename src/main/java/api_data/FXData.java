package api_data;

import api_connection.FXRatesService;
import api_connection.FXRatesServiceFactory;
import api_connection.RealTimeFXRate;
import api_connection.TimeSeries;
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

        FXRatesServiceFactory factory = new FXRatesServiceFactory();
        FXRatesService fxRatesService = factory.getInstance();

        getTimeSeries(fxRatesService);
        getRealTimeFXRate(fxRatesService);
    }

    private void getTimeSeries(FXRatesService service) {
        try {
            Response<TimeSeries> response = service.getFXData(ticker).execute();
            timeSeries = response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getRealTimeFXRate(FXRatesService service) {
        try{
            Response<RealTimeFXRate> response = service.getRealTimeFXRate(ticker).execute();
            realTimeFXRate = response.body();
        } catch (IOException e){
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
     * returns realtime currency exchange rate
     */
    public double getRealTimeFXRate() {
        String rate = realTimeFXRate.realTimeExchangeRate.get("5. Exchange Rate");
        return Double.parseDouble(rate);
    }

}
