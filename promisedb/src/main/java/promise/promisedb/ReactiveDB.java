/*
 *
 *  * Copyright 2017, Peter Vincent
 *  * Licensed under the Apache License, Version 2.0, Promise.
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  * Unless required by applicable law or agreed to in writing,
 *  * software distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package promise.promisedb;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.Nullable;

import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import promise.Promise;
import promise.data.log.LogUtil;
import promise.model.List;
import promise.model.Result;
import promise.model.SList;
import promise.model.SModel;
import promise.model.function.MapFunction;
import promise.promisedb.query.QueryBuilder;
import promise.util.Conditions;
import promise.util.DoubleConverter;

/**
 *
 */
public abstract class ReactiveDB extends SQLiteOpenHelper implements ReactiveCrud<SQLiteDatabase> {
  /**
   *
   */
  private static final String DEFAULT_NAME = "fast";
  private String TAG = LogUtil.makeTag(ReactiveDB.class);
  /**
   *
   */
  private Context context;

  /**
   * @param name
   * @param factory
   * @param version
   * @param errorHandler
   */
  private ReactiveDB(
      String name,
      SQLiteDatabase.CursorFactory factory,
      int version,
      DatabaseErrorHandler errorHandler) {
    super(Promise.instance().context(), name, factory, version, errorHandler);
    LogUtil.d(TAG, "fast db init");
    this.context = Promise.instance().context();
    Promise.instance().listen(Promise.TAG, new Result<>()
        .responseCallBack(o -> {
          if (o instanceof String) {
            if (o.equals(Promise.CLEANING_UP_RESOURCES)) {
              CompositeDisposable disposable = onTerminate();
              if (disposable != null) disposable.dispose();
            }
          }
        }));
  }

  /**
   * @param name
   * @param version
   * @param cursorListener
   * @param listener
   */
  @TargetApi(Build.VERSION_CODES.HONEYCOMB)
  public ReactiveDB(String name, int version, final FastDbCursorFactory.Listener cursorListener, final Corrupt listener) {
    this(
        name,
        cursorListener != null ? new FastDbCursorFactory(cursorListener) : null,
        version,
        dbObj -> {
          assert listener != null;
          listener.onCorrupt();
        });
  }

  /**
   * @param version
   */
  public ReactiveDB(int version) {
    this(DEFAULT_NAME, version, null, null);
  }

  /*private void initTables() {
    indexCreatedTableHashMap = new ArrayMap<>();
    List<Table<?, SQLiteDatabase>> tables = Conditions.checkNotNull(tables());
    for (int i = 0; i < tables.size(); i++)
        indexCreatedTableHashMap.put(new IndexCreated(i, false), tables.get(i));
  }*/

  /**
   * @param table
   * @param <T>
   * @return
   */
  private <T extends SModel> Model<T> checkTableExist(Table<T, SQLiteDatabase> table) {

    try {
      return model(Conditions.checkNotNull(table));
    } catch (ModelError modelError) {
      LogUtil.e(TAG, "model conversion error ", modelError);
      return null;
    }
  }

  /**
   * @param table
   * @return
   */
  private Model checkTableExistWithoutType(Table<?, SQLiteDatabase> table) {

    try {
      return modelWithoutType(Conditions.checkNotNull(table));
    } catch (ModelError modelError) {
      LogUtil.e(TAG, "model conversion error ", modelError);
      return null;
    }
  }

  /**
   * @param db
   */
  @Override
  public final void onCreate(SQLiteDatabase db) {
    create(db);
  }

  /**
   * @param database
   * @param oldVersion
   * @param newVersion
   */
  @Override
  public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
    LogUtil.d(TAG, "onUpgrade", oldVersion, newVersion);
    if (shouldUpgrade(database, oldVersion, newVersion)) {
      LogUtil.d(TAG, "onUpgrade", "upgrading tables");
      upgrade(database, oldVersion, newVersion);
    }
  }

  /**
   * @param database
   * @param oldVersion
   * @param newVersion
   * @return
   */
  public abstract boolean shouldUpgrade(SQLiteDatabase database, int oldVersion, int newVersion);

  /**
   * @return
   */
  public String name() {
    return this.getDatabaseName();
  }

  /**
   * @return
   */
  public abstract List<Table<?, SQLiteDatabase>> tables();

  /**
   * @param database
   */
  private void create(SQLiteDatabase database) {
    boolean created = true;
    for (Table<?, SQLiteDatabase> table : Conditions.checkNotNull(tables())) {
      try {
        created = created && create(table, database);
      } catch (DBError dbError) {
        LogUtil.e(TAG, dbError);
        return;
      }
    }
  }

  /**
   * @param database
   * @param v1
   * @param v2
   */
  private void upgrade(SQLiteDatabase database, int v1, int v2) {
    for (Table<?, SQLiteDatabase> table : Conditions.checkNotNull(tables())) {
      try {
        if ((v2 - v1) == 1) table.onUpgrade(database, v1, v2);
        else {
          int i = v1;
          while (i < v2) {
            table.onUpgrade(database, i, i + 1);
            i++;
          }
        }
      } catch (ModelError modelError) {
        LogUtil.e(TAG, modelError);
        return;
      }
    }
  }

  /**
   * @param database
   * @param tables
   * @return
   */
  public boolean add(SQLiteDatabase database, List<Table<?, SQLiteDatabase>> tables) {
    boolean created = true;
    for (Table<?, SQLiteDatabase> table : tables) {
      try {
        created = created && create(table, database);
      } catch (DBError dbError) {
        LogUtil.e(TAG, dbError);
        return false;
      }
    }
    return created;
  }

  /**
   * @param database
   * @return
   */
  private boolean drop(SQLiteDatabase database) {
    boolean dropped = true;
    /*for (Map.Entry<IndexCreated, Table<?, SQLiteDatabase>> entry :
        indexCreatedTableHashMap.entrySet()) {
      try {
        dropped = dropped && drop(checkTableExist(entry.getValue()), database);
      } catch (DBError dbError) {
        dbError.printStackTrace();
        return false;
      }
    }*/
    return dropped;
  }

  /**
   * @param table
   * @param database
   * @return
   * @throws DBError
   */
  private boolean create(Table<?, SQLiteDatabase> table, SQLiteDatabase database) throws DBError {
    try {
      table.onCreate(database);
    } catch (ModelError e) {
      throw new DBError(e);
    }
    return true;
  }

  /**
   * @param table
   * @param database
   * @param <T>
   * @return
   */
  private <T extends SModel> Single<Boolean> drop(Table<T, SQLiteDatabase> table, SQLiteDatabase database) {
    return Single.fromCallable(() -> table.onDrop(database))
        .subscribeOn(Schedulers.from(Promise.instance().executor()));
  }

  /**
   * @param builder
   * @return
   */
  public Single<Cursor> query(final QueryBuilder builder) {
    return Single.fromCallable(() -> {
      String sql = builder.build();
      String[] params = builder.buildParameters();
      return getReadableDatabase().rawQuery(sql, params);
    }).subscribeOn(Schedulers.from(Promise.instance().executor()));
  }

  /**
   * @return
   */
  public Context getContext() {
    return context;
  }

  /**
   * @param table
   * @param <T>
   * @return
   */
  @Override
  public <T extends SModel> ReactiveTable.Extras<T> read(Table<T, SQLiteDatabase> table) {
    return new QueryExtras<T>(checkTableExist(table), getReadableDatabase()) {
      @Override
      public ContentValues serialize(T t) {
        return checkTableExist(table).serialize(t);
      }

      @Override
      public T deserialize(Cursor cursor) {
        return checkTableExist(table).deserialize(cursor);
      }
    };
  }

  /**
   * @param table
   * @param <T>
   * @return
   */
  @Override
  public <T extends SModel> Maybe<SList<T>> readAll(Table<T, SQLiteDatabase> table) {
    return Maybe.fromCallable(() -> table.onReadAll(getReadableDatabase(), true))
        .subscribeOn(Schedulers.from(Promise.instance().executor()));
  }

  /**
   * @param table
   * @param column
   * @param <T>
   * @return
   */
  @Override
  public <T extends SModel> Maybe<SList<T>> readAll(Table<T, SQLiteDatabase> table, Column... column) {
    return Maybe.fromCallable(() -> table.onReadAll(getReadableDatabase(), column))
        .subscribeOn(Schedulers.from(Promise.instance().executor()));
  }

  /**
   * @param t
   * @param table
   * @param column
   * @param <T>
   * @return
   */
  @Override
  public <T extends SModel> Maybe<Boolean> update(T t, Table<T, SQLiteDatabase> table, Column column) {
    return Maybe.fromCallable(() -> table.onUpdate(t, getWritableDatabase(), column))
        .subscribeOn(Schedulers.from(Promise.instance().executor()));
  }

  /**
   * @param t
   * @param table
   * @param <T>
   * @return
   */
  @Override
  public <T extends SModel> Maybe<Boolean> update(T t, Table<T, SQLiteDatabase> table) {
    return Maybe.fromCallable(() -> table.onUpdate(t, getWritableDatabase()))
        .subscribeOn(Schedulers.from(Promise.instance().executor()));
  }

  /**
   * @param table
   * @param column
   * @param <T>
   * @return
   */
  @Override
  public <T extends SModel> Maybe<Boolean> delete(Table<T, SQLiteDatabase> table, Column column) {
    return Maybe.fromCallable(() -> table.onDelete(getWritableDatabase(), column))
        .subscribeOn(Schedulers.from(Promise.instance().executor()));
  }

  /**
   * @param table
   * @param t
   * @param <T>
   * @return
   */
  @Override
  public <T extends SModel> Maybe<Boolean> delete(Table<T, SQLiteDatabase> table, T t) {
    return Maybe.fromCallable(() -> table.onDelete(t, getWritableDatabase()))
        .subscribeOn(Schedulers.from(Promise.instance().executor()));
  }

  /**
   * @param table
   * @return
   */
  @Override
  public Maybe<Boolean> delete(Table<?, SQLiteDatabase> table) {
    return Maybe.fromCallable(() -> table.onDelete(getWritableDatabase()))
        .subscribeOn(Schedulers.from(Promise.instance().executor()));
  }

  /**
   * @param table
   * @param column
   * @param list
   * @param <C>
   * @return
   */
  @Override
  public <C> Maybe<Boolean> delete(Table<?, SQLiteDatabase> table, Column<C> column, List<C> list) {
    return Maybe.fromCallable(() -> table.onDelete(getWritableDatabase(), column, list))
        .subscribeOn(Schedulers.from(Promise.instance().executor()));
  }

  /**
   * @param t
   * @param table
   * @param <T>
   * @return
   */
  @Override
  public <T extends SModel> Single<Long> save(T t, Table<T, SQLiteDatabase> table) {
    return Single.fromCallable(() -> table.onSave(t, getWritableDatabase()))
        .subscribeOn(Schedulers.from(Promise.instance().executor()));
  }

  /**
   * @param list
   * @param table
   * @param <T>
   * @return
   */
  @Override
  public <T extends SModel> Single<Boolean> save(SList<T> list, Table<T, SQLiteDatabase> table) {
    return Single.fromCallable(() -> table.onSave(list, getWritableDatabase()))
        .subscribeOn(Schedulers.from(Promise.instance().executor()));
  }

  /**
   * @return
   */
  @Override
  public Maybe<Boolean> deleteAll() {
    return Maybe.zip(tables().map((MapFunction<MaybeSource<?>, Table<?, SQLiteDatabase>>) this::delete),
        objects -> List.fromArray(objects).allMatch(aBoolean -> aBoolean instanceof Boolean &&
            (Boolean) aBoolean)).subscribeOn(Schedulers.from(Promise.instance().executor()));
  }

  /**
   * @param table
   * @param <T>
   * @return
   */
  @Override
  public <T extends SModel> Maybe<Integer> getLastId(Table<T, SQLiteDatabase> table) {
    return Maybe.fromCallable(() -> table.onGetLastId(getReadableDatabase()))
        .subscribeOn(Schedulers.from(Promise.instance().executor()));
  }

  /*private IndexCreated getIndexCreated(Table<?, SQLiteDatabase> table) {
    for (Iterator<Map.Entry<IndexCreated, Table<?, SQLiteDatabase>>> iterator =
            indexCreatedTableHashMap.entrySet().iterator();
        iterator.hasNext(); ) {
      Map.Entry<IndexCreated, Table<?, SQLiteDatabase>> entry = iterator.next();
      Table<?, SQLiteDatabase> table1 = entry.getValue();
      if (table1.getName().equalsIgnoreCase(table.getName())) return entry.getKey();
    }
    return new IndexCreated(0, false);
  }*/

  /**
   * @return
   */
  public abstract CompositeDisposable onTerminate();

  private static class IndexCreated {
    int id;
    boolean created;

    IndexCreated(int id, boolean created) {
      this.id = id;
      this.created = created;
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) return true;
      if (!(object instanceof IndexCreated)) return false;
      IndexCreated that = (IndexCreated) object;
      return id == that.id && created == that.created;
    }

    @Override
    public int hashCode() {
      int result = id;
      result = 31 * result + (created ? 1 : 0);
      return result;
    }
  }

  /**
   * @param <T>
   * @param table
   * @return
   * @throws ModelError
   */
  private <T extends SModel> Model<T> model(final Table<T, SQLiteDatabase> table) throws ModelError {
    if (!(table instanceof Model))
      throw new ModelError(new IllegalStateException("Passed table not a model instance"));
    return (Model<T>) table;
  }

  /**
   * @param table
   * @return
   * @throws ModelError
   */
  private Model modelWithoutType(final Table<?, SQLiteDatabase> table) throws ModelError {
    if (!(table instanceof Model))
      throw new ModelError(new IllegalStateException("Passed table not a model instance"));
    return (Model<?>) table;
  }

  /**
   *
   * @param <T>
   */
  private abstract class QueryExtras<T extends SModel> implements ReactiveTable.Extras<T>, DoubleConverter<T, Cursor, ContentValues> {

    private SQLiteDatabase database;
    private Model<T> table;

    QueryExtras(Model<T> table, SQLiteDatabase database) {
      this.table = table;
      this.database = database;
    }

    /**
     *
     * @return
     */
    @Nullable
    @Override
    public Maybe<T> first() {
      return Maybe.fromCallable(() -> table.read(database).first())
          .subscribeOn(Schedulers.from(Promise.instance().executor()));
    }

    /**
     *
     * @return
     */
    @Nullable
    @Override
    public Maybe<T> last() {
      return Maybe.fromCallable(() -> table.read(database).last())
          .subscribeOn(Schedulers.from(Promise.instance().executor()));
    }

    /**
     *
     * @return
     */
    @Override
    public Maybe<SList<T>> all() {
      return Maybe.fromCallable(() -> table.read(database).all())
          .subscribeOn(Schedulers.from(Promise.instance().executor()));
    }

    /**
     *
     * @param limit
     * @return
     */
    @Override
    public Maybe<SList<T>> limit(final int limit) {
      return Maybe.fromCallable(() -> table.read(database).limit(limit));
    }

    /**
     *
     * @param skip
     * @param limit
     * @return
     */
    @Override
    public Maybe<SList<T>> paginate(final int skip, final int limit) {
      return Maybe.fromCallable(() -> table.read(database).paginate(skip, limit))
          .subscribeOn(Schedulers.from(Promise.instance().executor()));
    }

    /**
     *
     * @param column
     * @param a
     * @param b
     * @return
     */
    @Override
    public <N extends Number> Maybe<SList<T>> between(final Column<N> column, final N a, final N b) {
      return Maybe.fromCallable(() -> table.read(database).between(column, a, b))
          .subscribeOn(Schedulers.from(Promise.instance().executor()));
    }

    /**
     *
     * @param column
     * @return
     */
    @Override
    public Maybe<SList<T>> where(final Column[] column) {
      return Maybe.fromCallable(() -> table.read(database).where(column))
          .subscribeOn(Schedulers.from(Promise.instance().executor()));
    }

    /**
     *
     * @param column
     * @param bounds
     * @return
     */
    @SafeVarargs
    @Override
    public final <N extends Number> Maybe<SList<T>> notIn(final Column<N> column, final N... bounds) {
      return Maybe.fromCallable(() -> table.read(database).notIn(column, bounds))
          .subscribeOn(Schedulers.from(Promise.instance().executor()));
    }

    /**
     *
     * @param column
     * @return
     */
    @Override
    public Maybe<SList<T>> like(final Column[] column) {
      return Maybe.fromCallable(() -> table.read(database).like(column))
          .subscribeOn(Schedulers.from(Promise.instance().executor()));
    }

    /**
     *
     * @param column
     * @return
     */
    @Override
    public Maybe<SList<T>> orderBy(final Column column) {
      return Maybe.fromCallable(() -> table.read(database).orderBy(column))
          .subscribeOn(Schedulers.from(Promise.instance().executor()));
    }

    /**
     *
     * @param column
     * @return
     */
    @Override
    public Maybe<SList<T>> groupBy(final Column column) {
      return Maybe.fromCallable(() -> table.read(database).groupBy(column))
          .subscribeOn(Schedulers.from(Promise.instance().executor()));
    }

    /**
     *
     * @param column
     * @param column1
     * @return
     */
    @Override
    public Maybe<SList<T>> groupAndOrderBy(final Column column, final Column column1) {
      return Maybe.fromCallable(() -> table.read(database).groupAndOrderBy(column, column1))
          .subscribeOn(Schedulers.from(Promise.instance().executor()));
    }
  }

}
