package promise.app.data.db.async

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteDatabase
import io.reactivex.disposables.CompositeDisposable
import promise.Promise
import promise.app.data.db.TodoTable
import promise.app.error.AppError
import promise.app.models.Todo
import promise.data.db.Corrupt
import promise.data.db.FastDbCursorFactory
import promise.data.db.ReactiveFastDB
import promise.data.db.Table
import promise.data.db.query.QueryBuilder
import promise.data.log.LogUtil
import promise.model.List
import promise.model.Message
import promise.model.ResponseCallBack
import promise.model.SList

class AsyncAppDatabase private constructor() : ReactiveFastDB(DB_NAME, DB_VERSION, FastDbCursorFactory.Listener { query ->
  LogUtil.d("_AsyncAppDatabase", "query: ", query)
},
    Corrupt { Promise.instance().send(Message(SENDER_TAG, "Database is corrupted")) }) {
  private val disposable = CompositeDisposable()

  override fun shouldUpgrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int): Boolean =
      newVersion > oldVersion

  override fun tables(): List<Table<*, SQLiteDatabase>> =
      object : List<Table<*, SQLiteDatabase>>() {
        init {
          add(todoTable)
        }
      }

  fun todos(skip: Int, limit: Int, responseCallBack: ResponseCallBack<List<Todo>, AppError>) {
    disposable.add(query(QueryBuilder().from(todoTable).skip(skip).take(limit))
        .subscribe({ cursor ->
          responseCallBack.response(object : List<Todo>() {
            init {
              while (cursor.moveToNext()) add(todoTable!!.from(cursor))
            }
          })
          cursor.close()
        }, { throwable ->
          responseCallBack.error(AppError(throwable))
        }))
  }

  /**
   * @param category
   */
  fun todos(category: String, responseCallBack: ResponseCallBack<List<Todo>, AppError>) {
    disposable.add(readAll(todoTable, TodoTable.category.with(category)).subscribe({ todos -> responseCallBack.response(todos) }, { throwable -> responseCallBack.error(AppError(throwable)) }))
  }

  /**
   * @param todos to be saved
   */
  fun saveTodos(todos: List<Todo>, responseCallBack: ResponseCallBack<Boolean, AppError>) {
    disposable.add(save(SList(todos), todoTable).subscribe({ aBoolean -> responseCallBack.response(aBoolean) }, { throwable -> responseCallBack.error(AppError(throwable)) }))
  }

  override fun onTerminate(): CompositeDisposable? = disposable

  companion object {

    private val DB_NAME = "a"
    private val DB_VERSION = 1
    val SENDER_TAG = "App_Database"
    @SuppressLint("StaticFieldLeak")
    private var instance: AsyncAppDatabase? = null

    fun instance(): AsyncAppDatabase {
      if (instance == null) instance = AsyncAppDatabase()
      return instance as AsyncAppDatabase
    }

    private var todoTable: TodoTable? = null

    init {
      todoTable = TodoTable()
    }
  }
}
