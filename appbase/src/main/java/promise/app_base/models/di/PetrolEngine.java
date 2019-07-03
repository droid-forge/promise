package promise.app_base.models.di;

import javax.inject.Inject;
import javax.inject.Named;

import promise.data.log.LogUtil;

public class PetrolEngine implements Engine {
  public static final String TAG = LogUtil.makeTag(PetrolEngine.class);

  private int horsePower;
  private int engineCapacity;


  @Inject
  public PetrolEngine(@Named("horsePower") int horsePower,
                      @Named("engineCapacity") int engineCapacity) {
    this.horsePower = horsePower;
    this.engineCapacity = engineCapacity;
  }

  @Override
  public void start() {
    LogUtil.e(TAG,
        "petrol engine started... : horsepower: ", horsePower, " engine capacity: ", engineCapacity);
  }
}
