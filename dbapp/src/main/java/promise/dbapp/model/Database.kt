package promise.dbapp.model

import android.database.sqlite.SQLiteDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import promise.model.List
import promise.model.Result
import promise.model.SList
import promise.promisedb.ReactiveDB
import promise.promisedb.Table

class Database : ReactiveDB(name, version, null, null) {
  /**
   *
   */
  private val compositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }
  /**
   * @return
   */
  override fun onTerminate(): CompositeDisposable {
    return compositeDisposable
  }

  /**
   *
   * @param database
   * @param oldVersion
   * @param newVersion
   * @return
   */
  override fun shouldUpgrade(database: SQLiteDatabase?, oldVersion: Int, newVersion: Int): Boolean {
    return oldVersion < newVersion
  }

  /**
   *
   * @return
   */
  override fun tables(): List<Table<*, SQLiteDatabase>> = List.fromArray(complexModelTable)

  fun allComplexModels(result: Result<SList<ComplexModel>, Throwable>) {
    compositeDisposable.add(readAll(complexModelTable).observeOn(AndroidSchedulers.mainThread())
        .subscribe({ list ->
          if (list.isEmpty())
            saveSomeComplexModels(Result<Boolean, Throwable>()
                .responseCallBack { allComplexModels(result) }
                .errorCallBack { result.error(it) }) else result.response(list)

        }, {
          result.error(it)
        }))
  }

  private fun saveSomeComplexModels(result: Result<Boolean, Throwable>) {
    compositeDisposable.add(save(SList(ComplexModel.someModels()), complexModelTable)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
          result.response(it)
        }, {
          result.error(it)
        }))
  }


  companion object {
    const val name = "complex_db_name"
    const val version = 1
    private val complexModelTable: ComplexModelTable by lazy { ComplexModelTable() }
  }
}