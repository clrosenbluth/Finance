package api_connection;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FXRateServiceFactory {

    public FXRateService getInstance() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.exchangerate.host/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(FXRateService.class);
    }
}
