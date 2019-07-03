package promise.app_base;

import dagger.Module;
import dagger.Provides;
import promise.Promise;
import promise.app_base.scopes.AppScope;

@Module
public abstract class DependenciesModule {

  @AppScope
  @Provides
  public static Promise providePromise() {
    return Promise.instance();
  }
}
