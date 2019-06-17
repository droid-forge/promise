package promise.app;

import android.app.Application;

import promise.Promise;

public class App extends Application {
  private AppComponent appComponent;

  @Override
  public void onCreate() {
    super.onCreate();
    Promise.init(this).threads(100);
    appComponent = DaggerAppComponent.create();

  }

  public AppComponent getAppComponent() {
    return appComponent;
  }
}
