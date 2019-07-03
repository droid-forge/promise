package promise.app_base.common


import promise.app_base.data.db.async.AsyncAppDatabase
import promise.app_base.data.net.TodoApi
import promise.app_base.error.AppError
import promise.app_base.models.Todo
import promise.data.net.net.Call
import promise.data.net.net.Callback
import promise.data.net.net.Response
import promise.model.List
import promise.model.ResponseCallBack
import promise.repo.AbstractAsyncIDataStore

class TodoAsyncStore(private val asyncAppDatabase: AsyncAppDatabase, private val todoApi: TodoApi) : AbstractAsyncIDataStore<Todo>() {
  override fun all(res: (List<Todo>) -> Unit, err: ((Exception) -> Unit)?, args: Map<String, Any?>?) {
    if (args == null || args[FilterTypes.PAGES.name] == null) throw IllegalStateException("args must be passed here")
    val requestedPages = args[FilterTypes.PAGES.name] as List<Int>
    asyncAppDatabase.todos(requestedPages[0], requestedPages[1], ResponseCallBack<List<Todo>, AppError>()
        .response { todos ->
          if (todos.isEmpty())
            todoApi.todos(requestedPages[0], requestedPages[1]).enqueue(object : Callback<kotlin.collections.List<Todo>> {
              override fun onResponse(call: Call<kotlin.collections.List<Todo>>?, ress: Response<kotlin.collections.List<Todo>>?) =
                  res(ress?.body()?.let { List(it) }!!)
              override fun onFailure(call: Call<kotlin.collections.List<Todo>>?, t: Throwable?) {
                err?.invoke(t?.let { AppError(it) }!!)
              }
            })
          else
            res(todos)
        }
        .error {
          err?.invoke(it)
        })
  }
}