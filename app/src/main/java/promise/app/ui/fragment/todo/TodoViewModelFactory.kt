package promise.app.ui.fragment.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import promise.Promise
import promise.app.scopes.UIScope
import promise.app_base.models.Todo
import promise.app_base.repos.ReposModule
import promise.repo.StoreRepository
import javax.inject.Inject
import javax.inject.Named

/**
 *
 *
 */
@UIScope
class TodoViewModelFactory @Inject constructor(@Named(ReposModule.TODO_REPOSITORY)
                                               var todoRepository: StoreRepository<Todo>,
                                               val promise: Promise) : ViewModelProvider.Factory {

  /**
   * creates a TodoModelView
   *
   * @param T
   * @param modelClass
   * @return
   */
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(TodoViewModel::class.java)) return TodoViewModel(
        todoRepository, promise
    ) as T
    else throw IllegalArgumentException("Unknown ViewModel class")
  }

}