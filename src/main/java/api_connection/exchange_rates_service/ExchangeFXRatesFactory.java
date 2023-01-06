package api_connection.exchange_rates_service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ExchangeFXRatesFactory {
    public ExchangeFXRatesService getInstance() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.alphavantage.co/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(ExchangeFXRatesService.class);
    }
}
