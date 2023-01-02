package api_data;

import api_connection.FXDailyService;
import api_connection.FXDailyServiceFactory;
import api_connection.TimeSeries;
import com.google.gson.internal.LinkedTreeMap;
import retrofit2.Response;

import java.io.IOException;

public class FXData {
    private TimeSeries timeSeries = null;

    /* ticker should be formatted as "ABC" */
    public FXData(String ticker) {
        FXDailyServiceFactory factory = new FXDailyServiceFactory();
        FXDailyService service = factory.getInstance();

        try {
            Response<TimeSeries> response = service.getFXData(ticker).execute();
            timeSeries = response.body();
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
}
