package api_connection;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FXRateService {
     String api_key = "556BNVOJG7TA17H2";

    /**
     *
     * @param to_symbol
     * @return full available days worth of data with close, open, etc per day for fx specified
     */
    @GET("query?function=FX_DAILY&from_symbol=USD&outputsize=full&apikey=" + api_key)
    Call<TimeSeries> getDailyFXData(@Query("to_symbol") String to_symbol);

    /**
     *
     *
     * @param to_currency
     * @return Realtime FX rate
     */
    @GET("query?function=CURRENCY_EXCHANGE_RATE&from_currency=USD&apikey=" + api_key)
    Call<RealTimeFXRate> getRealTimeFXRate(@Query("to_currency") String to_currency);

}