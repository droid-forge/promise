package promise.app.models.di;

import javax.inject.Inject;

import promise.data.log.LogUtil;

public class Remote {
  public static final String TAG = LogUtil.makeTag(Remote.class);

  @Inject
  public Remote() {
  }

  public void setListener(Car car) {
    LogUtil.e(TAG, "remote connected...");
  }
}
