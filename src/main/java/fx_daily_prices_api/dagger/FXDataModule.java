package fx_daily_prices_api.dagger;

import dagger.Module;
import dagger.Provides;
import fx_daily_prices_api.service.FXDailyService;
import fx_daily_prices_api.service.FXDailyServiceFactory;

import javax.inject.Singleton;

@Module
public class FXDataModule {

    @Singleton
    @Provides
    public FXDailyService providesFXDailyService(
            FXDailyServiceFactory factory) {
        return factory.getInstance();
    }
}
