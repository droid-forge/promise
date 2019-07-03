package promise.app

import dagger.Component
import promise.Promise
import promise.app.ui.fragment.di.FragmentComponent
import promise.app_base.DependenciesModule
import promise.app_base.data.net.BaseApi
import promise.app_base.models.di.DieselEngineModule
import promise.app_base.models.di.DriverModule
import promise.app_base.repos.AuthRepository
import promise.app_base.repos.ReposModule
import promise.app_base.scopes.AppScope

/**
 * AppComponent is the main application component
 * @see DriverModule
 * @see BaseApi
 * @see DependenciesModule
 * defines exposed dependencies on global app scope
 * depends on BaseApi for various apis and dependencies module for external dependencies like promise
 *
 */
@AppScope
@Component(modules = [DriverModule::class, BaseApi::class,
  DependenciesModule::class, ReposModule::class])
interface AppComponent {

  /**
   * Exposes Promise to the UI based components
   *
   * @return Promise instance
   */
  fun getPromise(): Promise

  /**
   * Exposes AuthRepository to the UI based components
   * @see AuthRepository
   *
   * @return instance of auth repository
   */
  fun getAuthRepository(): AuthRepository

  fun getFragmentComponent(dieselEngineModule: DieselEngineModule): FragmentComponent
}