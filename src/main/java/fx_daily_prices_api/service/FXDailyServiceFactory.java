package fx_daily_prices_api.service;

import fx_daily_prices_api.service.FXDailyService;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.inject.Inject;

public class FXDailyServiceFactory {

    @Inject
    public FXDailyServiceFactory() {

    }

    public FXDailyService getInstance() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.alphavantage.co/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit.create(FXDailyService.class);
    }
}
