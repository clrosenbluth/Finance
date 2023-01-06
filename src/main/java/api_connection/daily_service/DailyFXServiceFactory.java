package api_connection.daily_service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DailyFXServiceFactory {

    public DailyFXService getInstance() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.alphavantage.co/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(DailyFXService.class);
    }
}
