package promise.app;

import android.app.Application;

import promise.Promise;

public class App extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    Promise.init(this).threads(100);

  }

}
