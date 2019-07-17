package promise.app

import dagger.Component
import promise.Promise
import promise.app.ui.fragment.di.FragmentComponent
import promise.app_base.DependenciesModule
import promise.app_base.data.net.BaseApi
import promise.app_base.models.Todo
import promise.app_base.models.di.DieselEngineModule
import promise.app_base.models.di.DriverModule
import promise.app_base.repos.AuthRepository
import promise.app_base.repos.ReposModule
import promise.app_base.scopes.AppScope
import promise.repo.StoreRepository
import javax.inject.Named

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
@Component(modules = [DriverModule::class, BaseApi::class, DependenciesModule::class, ReposModule::class])
interface AppComponent {

  @Named(ReposModule.TODO_REPOSITORY)
  fun todoRepository(): StoreRepository<Todo>

  fun getAuthRepository(): AuthRepository

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


  fun getFragmentComponent(dieselEngineModule: DieselEngineModule): FragmentComponent
}