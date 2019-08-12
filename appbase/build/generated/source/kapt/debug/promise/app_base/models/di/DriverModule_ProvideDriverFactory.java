// Generated by Dagger (https://google.github.io/dagger).
package promise.app_base.models.di;

import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class DriverModule_ProvideDriverFactory implements Factory<Driver> {
  private static final DriverModule_ProvideDriverFactory INSTANCE = new DriverModule_ProvideDriverFactory();

  @Override
  public Driver get() {
    return provideDriver();
  }

  public static DriverModule_ProvideDriverFactory create() {
    return INSTANCE;
  }

  public static Driver provideDriver() {
    return Preconditions.checkNotNull(DriverModule.provideDriver(), "Cannot return null from a non-@Nullable @Provides method");
  }
}