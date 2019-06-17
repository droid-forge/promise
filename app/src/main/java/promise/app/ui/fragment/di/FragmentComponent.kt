package promise.app.ui.fragment.di

import dagger.Subcomponent
import promise.app.models.di.Car
import promise.app.models.di.DieselEngineModule
import promise.app.models.di.PerFragment
import promise.app.models.di.WheelsModule

@PerFragment
@Subcomponent(modules = [WheelsModule::class, DieselEngineModule::class])
interface FragmentComponent {

  fun getCar(): Car

  fun inject(diFragment: DIFragment)

  /*@Component.Builder
  interface Builder {
    @BindsInstance
    fun horsePower(@Named("horsePower") horsePower: Int): Builder
    @BindsInstance
    fun engineCapacity(@Named("engineCapacity") engineCapacity: Int): Builder

    fun appComponent( appComponent: AppComponent): Builder

    fun build(): FragmentComponent
  }*/
}