package promise.app

import dagger.Component
import promise.app.scopes.UIScope
import promise.app.ui.ListAdapterModule
import promise.app.ui.activity.login.LoginActivity
import promise.app.ui.fragment.todo.TodoFragment
import promise.app.ui.fragment.todo.TodoViewModelFactory
import promise.app_base.models.Todo
import promise.app_base.repos.ReposModule
import promise.repo.StoreRepository
import javax.inject.Named

/**
 * Provides dependencies for components marked with @UIScope
 * Depends on AppComponent to references application scoped dependencies
 * @see AppComponent
 * @see UIScope
 *
 */
@Component(dependencies = [AppComponent::class], modules = [ListAdapterModule::class])
@UIScope
interface UIComponent {

  /**
   * Injects LoginViewModelFactory into Login Activity
   *
   * @see promise.app.ui.activity.login.LoginViewModelFactory
   * @param loginActivity instance to inject view model into
   */
  fun inject(loginActivity: LoginActivity)

  fun inject(todoFragment: TodoFragment)

  /**
   * Since we've used the dependencies in this component, we need to add a custom builder
   * to inject an instance of appComponent into uiComponent
   *
   */
  /*@Component.Builder
  interface Builder {
    *//**
     * inject instance of appComponent
     *
     * @param appComponent
     * @return Builder
     *//*
    fun appComponent(appComponent: AppComponent): Builder

    *//**
     * finally build the component
     *
     * @return Build UIComponent
     *//*
    fun build(): UIComponent
  }*/

}