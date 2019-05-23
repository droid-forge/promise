package promise.app.common

import promise.app.data.db.AppDatabase
import promise.app.data.net.TodoApi
import promise.app.models.Todo
import promise.model.List
import promise.repo.SyncIDataStore

class TodoSyncStore(private val appDatabase: AppDatabase, private val todoApi: TodoApi) : SyncIDataStore<Todo> {
  override fun all(args: Map<String, Any?>?): List<Todo>? {
    if (args == null || args[FilterTypes.PAGES.name] == null) throw IllegalStateException("args must be passed here")
    val requestedPages = args[FilterTypes.PAGES.name] as List<Int>
    val todos = appDatabase.todos(requestedPages[0], requestedPages[1])
    return if (todos.isEmpty())
      List(todoApi.todos(requestedPages[0], requestedPages[1]).execute().body()!!)
    else todos
  }

  override fun one(args: Map<String, Any?>?): Todo? {
    return null
  }

  override fun save(t: Todo, args: Map<String, Any?>?): Pair<Todo, Any?> {
    return Pair(Todo(), null)
  }

  override fun update(t: Todo, args: Map<String, Any?>?): Pair<Todo, Any?> {
    return Pair(Todo(), null)
  }

  override fun delete(t: Todo, args: Map<String, Any?>?): Any? {
    return null
  }

  override fun clear(args: Map<String, Any?>?): Any? {
    return null
  }
}