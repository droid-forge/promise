package promise.app.models.di;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class PetrolEngineModule {


  @Binds
  abstract Engine bindsEngine(PetrolEngine petrolEngine);
}

