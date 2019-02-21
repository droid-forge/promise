package me.dev4vin.model;

import me.dev4vin.data.Store;

/** Created on 7/17/18 by yoctopus. */
public abstract class ChainProcessor {
  public abstract List<Store<?, ?, Throwable>> stores();

  public void terminate() {
    for (Store<?, ?, Throwable> store : stores())
      store.clear(new ResponseCallBack<Boolean, Throwable>());
  }
}
