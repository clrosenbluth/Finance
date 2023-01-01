package api_connection;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FXDailyService {
    String api_key = "QMO1ITTPN9C2MWVC";

    /* gets 100 days worth of data
     * add parameter outputsize=full for 2115 days
     */
    @GET("query?function=FX_DAILY&from_symbol=USD&apikey=" + api_key)
    Call<TimeSeries> getFXData(@Query("to_symbol") String to_symbol);
}