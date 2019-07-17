package promise.app;

import android.app.Application;

import promise.Promise;
import promise.app_base.scopes.AppScope;

/**
 * The main application class for hosting the components scoped through the life cycle of the application
 *
 * @author g33k
 * @see AppScope
 * * The type App.
 */
public class App extends Application {

  /**
   * AppComponent holds all injections with the @AppScope annotation
   *
   * @see AppScope
   */
  private AppComponent appComponent;

  /**
   * initialize promise and AppComponent here
   */
  @Override
  public void onCreate() {
    super.onCreate();
    Promise.init(this).threads(100);
    appComponent = DaggerAppComponent.create();

  }

  /**
   * Gets app component.
   *
   * @return the app component
   */
  public AppComponent getAppComponent() {
    return appComponent;
  }
}
