package api_connection;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FXRateService {
    @GET("timeseries?base=USD")
    Call<DailyFXRate> getDailyFXData(@Query("start_date") String start_date,
                                     @Query("end_date") String end_date);

    /**
     * @return Realtime FX rate
     */
    @GET("latest?base=USD")
    Call<RealTimeFXRate> getRealTimeFXRate();

}