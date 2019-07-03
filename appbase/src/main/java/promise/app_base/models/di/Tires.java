package promise.app_base.models.di;

import promise.data.log.LogUtil;

public class Tires {
  public static final String TAG = LogUtil.makeTag(Tires.class);
  public void inflate() {
    LogUtil.e(TAG, "tires inflated...");
  }
}
