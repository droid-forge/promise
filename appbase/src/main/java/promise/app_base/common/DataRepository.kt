package promise.app_base.common

import promise.app_base.data.db.AppDatabase
import promise.app_base.data.db.async.AsyncAppDatabase
import promise.app_base.data.net.TodoApi
import promise.app_base.models.Todo
import promise.data.net.converters.GsonConverterFactory
import promise.data.net.net.FastParserEngine
import promise.model.List
import promise.model.ResponseCallBack
import promise.repo.StoreRepository

enum class FilterTypes { PAGES, CATEGORY }

class FilterMode {

  lateinit var arg: Any
  lateinit var filterTypes: FilterTypes

  fun toMap(): MutableMap<String, Any?> = mutableMapOf(Pair(filterTypes.name, arg))

  companion object {

    fun pages(skip: Int, take: Int): FilterMode = FilterMode().apply {
      arg = List.fromArray(skip, take)
      filterTypes = FilterTypes.PAGES
    }

    fun category(category: String): FilterMode = FilterMode().apply {
      arg = category
      filterTypes = FilterTypes.CATEGORY
    }
  }
}


class DataRepository private constructor() {

  private val appDatabase: AppDatabase by lazy { AppDatabase.instance() }

  private val asyncAppDatabase: AsyncAppDatabase by lazy {
    AsyncAppDatabase.instance()
  }

  private val todoApi: TodoApi by lazy {
    FastParserEngine.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(TodoApi::class.java)
  }

  private val todoRepository: StoreRepository<Todo> by lazy {
    StoreRepository.create(TodoSyncStore(appDatabase, todoApi), TodoAsyncStore(asyncAppDatabase, todoApi))
  }

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
    todoRepository.all(FilterMode.pages(skip, limit).toMap(), {
      responseCallBack.response(it)
    }, {
      responseCallBack.error(it)
    })
  }

  fun syngGetTodos(skip: Int, limit: Int): List<Todo>? = todoRepository.all(FilterMode.pages(skip, limit).toMap())

  companion object {
    private var instance: DataRepository? = null

    fun instance(): DataRepository {
      if (instance == null) instance = DataRepository()
      return instance as DataRepository
    }
  }
}
