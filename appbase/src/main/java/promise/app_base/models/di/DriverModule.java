package promise.app_base.models.di;

import dagger.Module;
import dagger.Provides;
import promise.app_base.scopes.AppScope;

@Module
public abstract class DriverModule {
  @Provides
  @AppScope
  static Driver provideDriver() {
    return new Driver();
  }
}
