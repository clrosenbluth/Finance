package fx_daily_prices_api.dagger;

import dagger.Component;
import frame.FinanceFrame;

import javax.inject.Singleton;

@Singleton
@Component(modules = {FXDataModule.class})
public interface FXDataComponent {

    FinanceFrame getFinanceFrame();
}
