package promise.app

import dagger.Component
import promise.app.models.di.DieselEngineModule
import promise.app.models.di.Driver
import promise.app.models.di.DriverModule
import promise.app.ui.fragment.di.FragmentComponent
import javax.inject.Singleton

@Singleton
@Component(modules = [DriverModule::class])
interface AppComponent {
  fun getFragmentComponent(dieselEngineModule: DieselEngineModule): FragmentComponent
}