package fx_daily_prices_api.service;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FXDailyService {

    String api_key = "QMO1ITTPN9C2MWVC";

    @GET("query?function=FX_DAILY&from_symbol=USD&apikey=" + api_key)
    Single<FXData> getFXData(@Query("to_symbol") String to_symbol);
}
