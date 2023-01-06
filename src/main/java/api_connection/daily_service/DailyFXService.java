package api_connection.daily_service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DailyFXService {
     String api_key = "556BNVOJG7TA17H2";

    /* gets 100 days worth of data
     * add parameter outputsize=full for 2115 days
     */
    @GET("query?function=FX_DAILY&from_symbol=USD&apikey=" + api_key)
    Call<TimeSeries> getDailyFXData(@Query("to_symbol") String to_symbol);

}