package promise.app_base.models.di;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public class DieselEngineModule {
  private int horsePower;

  public DieselEngineModule(int horsePower) {
    this.horsePower = horsePower;
  }

  @Provides
  int providesHorsePower() {
    return horsePower;
  }

  @Provides
   Engine providesEngine(DieselEngine engine) {
    return engine;
  }

}

