//package api_data;
//
//import api_connection.FXRateService;
//import api_connection.FXRateServiceFactory;
//import api_connection.DailyFXRate;
//import api_connection.RealTimeFXRate;
//import com.google.gson.internal.LinkedTreeMap;
//import retrofit2.Response;
//
//import java.io.IOException;
//
//public class RealTimeFXData {
//    private final String ticker;
//    private DailyFXRate fxRate = null;
//    private RealTimeFXRate realTimeFXRate = null;
//
//
//    /* ticker should be formatted as "ABC"
//    * date - create default date*/
//    public RealTimeFXData(String ticker, String date) {
//        this.ticker = ticker;
//
//        FXRateServiceFactory fxRateServiceFactory = new FXRateServiceFactory();
//        FXRateService fxRateService = fxRateServiceFactory.getInstance();
//
//        try {
//            Response<DailyFXRate> timeSeriesResponse = fxRateService.getDailyFXData(ticker, date).execute();
//            fxRate = timeSeriesResponse.body();
//            Response<RealTimeFXRate> realTimeFXRateResponse = fxRateService.getRealTimeFXRate(ticker).execute();
//            realTimeFXRate = realTimeFXRateResponse.body();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * @param date format as "YYYY-MM-DD"
//     * @return returns closing price in terms of foreign currency/USD
//     *          ex: returns 3.50920 when buying/selling ILS
//     */
//    public Double getClose(String date) {
//        LinkedTreeMap<String, String> dateMap = fxRate.timeSeriesFX.get(date);
//        return Double.parseDouble(dateMap.get("4. close"));
//    }
//
//    /**
//     *
//     * @return realtime currency exchange rate
//     */
//    public double getRealTimeFXRate() {
//        String rate = realTimeFXRate.realTimeExchangeRate.get("5. Exchange Rate");
//        return Double.parseDouble(rate);
//    }
//
//}
