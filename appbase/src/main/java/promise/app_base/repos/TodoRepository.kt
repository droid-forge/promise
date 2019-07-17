package promise.app_base.repos

import promise.app_base.data.db.async.AsyncAppDatabase
import promise.app_base.data.net.TodoApi
import promise.app_base.error.AppError
import promise.app_base.models.Todo
import promise.app_base.scopes.AppScope
import promise.data.log.LogUtil
import promise.data.net.net.Call
import promise.data.net.net.Callback
import promise.data.net.net.Response
import promise.model.List
import promise.model.ResponseCallBack
import promise.repo.AbstractAsyncIDataStore
import promise.repo.AbstractSyncIDataStore
import java.lang.IllegalArgumentException
import javax.inject.Inject

const val LIMIT_KEY = "limit_key"
const val SKIP_KEY = "skip_key"
const val TAG = "_TodoRepository"
@AppScope
class SyncTodoRepository @Inject constructor(): AbstractSyncIDataStore<Todo>()

@AppScope
class AsyncTodoRepository @Inject constructor(private val asyncAppDatabase: AsyncAppDatabase,
                                              private val todoApi: TodoApi
                                              ): AbstractAsyncIDataStore<Todo>() {
  override fun all(res: (List<Todo>) -> Unit, err: ((Exception) -> Unit)?, args: Map<String, Any?>?) {
    if (args == null) throw IllegalArgumentException("args must be passed here")
    val limit = args[LIMIT_KEY] as Int
    val skip = args[SKIP_KEY] as Int
    asyncAppDatabase.todos(skip, limit, ResponseCallBack<List<Todo>, AppError>()
        .response { todos ->
          if (todos.isEmpty()) todoApi.todos().enqueue(object : Callback<kotlin.collections.List<Todo>> {
            override fun onResponse(call: Call<kotlin.collections.List<Todo>>, response: Response<kotlin.collections.List<Todo>>) {
              LogUtil.e(TAG, "response", response)
              val list = response.body()
              if (list != null) {
                asyncAppDatabase.saveTodos(List(list), ResponseCallBack<Boolean, AppError>()
                    .response {
                      res(todos)
                    }
                    .error {
                      err?.invoke(it)
                    })
              } else {
                err?.invoke(AppError("could not load todos $response"))
              }
            }
            override fun onFailure(call: Call<kotlin.collections.List<Todo>>, t: Throwable) {
              err?.invoke(AppError(t))
            }
          }) else res(todos)
        }
        .error {
          err?.invoke(it)
        })
  }
}