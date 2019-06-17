package promise.app.common

import promise.app.data.db.AppDatabase
import promise.app.data.net.TodoApi
import promise.app.models.Todo
import promise.model.List
import promise.repo.AbstractSyncIDataStore

class TodoSyncStore(private val appDatabase: AppDatabase, private val todoApi: TodoApi) : AbstractSyncIDataStore<Todo>() {
  override fun all(args: Map<String, Any?>?): List<Todo>? {
    if (args == null || args[FilterTypes.PAGES.name] == null) throw IllegalStateException("args must be passed here")
    val requestedPages = args[FilterTypes.PAGES.name] as List<Int>
    val todos = appDatabase.todos(requestedPages[0], requestedPages[1])
    return if (todos.isEmpty())
      List(todoApi.todos(requestedPages[0], requestedPages[1]).execute().body()!!)
    else todos
  }
}