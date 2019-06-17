package promise.app.models.di;

import javax.inject.Inject;

import promise.data.log.LogUtil;

public class DieselEngine implements Engine {
  public static final String TAG = LogUtil.makeTag(DieselEngine.class);
  private int horsePower;

  @Inject
  public DieselEngine(int horsePower) {
    this.horsePower = horsePower;
  }

  @Override
  public void start() {
    LogUtil.e(TAG, "diesel engine started... : horsePower: ", horsePower);
  }
}
