package promise.app.common

import promise.app.data.db.AppDatabase
import promise.app.data.db.async.AsyncAppDatabase
import promise.app.data.net.ServerAPI
import promise.app.data.net.TodoApi
import promise.app.error.AppError
import promise.app.error.ServerError
import promise.app.models.Todo
import promise.model.List
import promise.model.ResponseCallBack

class DataRepository private constructor() {

  private val appDatabase: AppDatabase = AppDatabase.instance()
  /*private val serverAPI: ServerAPI = ServerAPI.instance()*/

  /**
   * get todos from the either the database or the upstream server
   * if todos are in the database, return the ones in the database hence fetch from server, store
   * in the database and return a fetch from the database
   *
   * @param skip             the todos to skip in the database
   * @param limit            the number of todos needed
   * @param responseCallBack return response back to caller
   */
  fun getTodos(skip: Int, limit: Int, responseCallBack: ResponseCallBack<List<Todo>, Exception>) {
    val todos = appDatabase.todos(skip, limit)
    if (todos.isEmpty())
      promise.app.data.net.getTodos(ResponseCallBack<List<Todo>, AppError>()
          .response {
            appDatabase.saveTodos(it)
            responseCallBack.response(it)
          }
          .error { responseCallBack.error(it) })
    else
      responseCallBack.response(todos)

  }

  companion object {
    private var instance: DataRepository? = null

    fun instance(): DataRepository {
      if (instance == null) instance = DataRepository()
      return instance as DataRepository
    }
  }
}
