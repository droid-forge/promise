package promise.app.models.di;

import javax.inject.Inject;

import promise.data.log.LogUtil;

@PerFragment
public class Car {

  private Driver driver;

  public static final String TAG = LogUtil.makeTag(Car.class);
  private Engine engine;
  private Wheels wheels;

  @Inject
  public Car(Driver driver, Engine engine, Wheels wheels) {
    this.driver = driver;
    this.engine = engine;
    this.wheels = wheels;
  }

  @Inject
  public void enableRemote(Remote remote) {
    remote.setListener(this);
  }

  public void drive() {
    //Vroom...
    engine.start();
    LogUtil.e(TAG, this.driver, "driving...: ", this);
  }
}
