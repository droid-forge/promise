package promise.app.ui

import dagger.BindsInstance
import dagger.Component
import promise.app.scopes.UIScope
import promise.app.ui.fragment.todo.TodoFragment
import javax.inject.Named

@Component(modules = [ListingDependencies::class])
@UIScope
interface ListingComponent {

  fun inject(todoFragment: TodoFragment)

  @Component.Builder
  interface Builder {

    fun listingDependenciesModule(listingDependencies: ListingDependencies): Builder

    @BindsInstance
    fun dividerLine(@Named(ListingDependencies.DIVIDER_PARAM) divider: Boolean): Builder

    fun build(): ListingComponent
  }

}