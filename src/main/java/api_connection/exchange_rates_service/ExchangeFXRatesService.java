package api_connection.exchange_rates_service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ExchangeFXRatesService {
    String api_key = "556BNVOJG7TA17H2";

    /**
     *
     * @param to_symbol
     * @return Realtime FX rate
     */
    @GET("query?function=CURRENCY_EXCHANGE_RATE&from_symbol=USD&apikey=" + api_key)
    Call<RealTimeFXRate> getRealTimeFXRate(@Query("to_symbol") String to_symbol);
}
