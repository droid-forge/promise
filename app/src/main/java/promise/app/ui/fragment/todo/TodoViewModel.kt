package promise.app.ui.fragment.todo

import androidx.collection.ArrayMap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import promise.Promise
import promise.app_base.models.Result
import promise.app_base.models.Todo
import promise.app_base.repos.LIMIT_KEY
import promise.app_base.repos.SKIP_KEY
import promise.model.List
import promise.model.Searchable
import promise.repo.StoreRepository

class TodoViewModel(private val todoRepository: StoreRepository<Todo>, private val promise: Promise) : ViewModel() {

   val data = MutableLiveData<Result<List<Searchable>>>()

   fun fetchTodos(skip: Int, limit: Int) = promise.execute {
      todoRepository.all(ArrayMap<String, Any>().apply {
         put(LIMIT_KEY, limit)
         put(SKIP_KEY, skip)
      }, {
         data.value = Result.Success(it.map { todo -> todo as Searchable })
      }, {
         data.value = Result.Error(it)
      })
   }

}
