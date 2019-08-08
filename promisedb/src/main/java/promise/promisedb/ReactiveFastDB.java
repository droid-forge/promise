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

import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import promise.Promise;
import promise.data.log.LogUtil;
import promise.model.List;
import promise.model.Result;
import promise.model.S;
import promise.model.SList;
import promise.model.SModel;
import promise.promisedb.query.QueryBuilder;
import promise.util.Conditions;

public abstract class ReactiveFastDB extends SQLiteOpenHelper implements ReactiveCrud<SQLiteDatabase> {
  private static final String DEFAULT_NAME = "fast";
  private String TAG = LogUtil.makeTag(ReactiveFastDB.class);
  private Context context;

  private ReactiveFastDB(
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

  @TargetApi(Build.VERSION_CODES.HONEYCOMB)
  public ReactiveFastDB(String name, int version,final FastDbCursorFactory.Listener cursorListener, final Corrupt listener) {
    this(
        name,
        cursorListener != null ? new FastDbCursorFactory(cursorListener) : null,
        version,
        dbObj -> {
          assert listener != null;
          listener.onCorrupt();
        });
  }

  public ReactiveFastDB(int version) {
    this(DEFAULT_NAME, version, null, null);
  }

  /*private void initTables() {
    indexCreatedTableHashMap = new ArrayMap<>();
    List<Table<?, SQLiteDatabase>> tables = Conditions.checkNotNull(tables());
    for (int i = 0; i < tables.size(); i++)
        indexCreatedTableHashMap.put(new IndexCreated(i, false), tables.get(i));
  }*/

  @Override
  public final void onCreate(SQLiteDatabase db) {
    create(db);
  }

  @Override
  public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
    LogUtil.d(TAG, "onUpgrade", oldVersion, newVersion);
    if (shouldUpgrade(database, oldVersion, newVersion)) {
      LogUtil.d(TAG, "onUpgrade", "upgrading tables");
      upgrade(database, oldVersion, newVersion);
    }
  }

  public abstract boolean shouldUpgrade(SQLiteDatabase database, int oldVersion, int newVersion);

  public String name() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
        ? this.getDatabaseName()
        : DEFAULT_NAME;
  }

  public abstract List<Table<?, SQLiteDatabase>> tables();

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

  private boolean create(Table<?, SQLiteDatabase> table, SQLiteDatabase database) throws DBError {
    try {
      table.onCreate(database);
    } catch (ModelError e) {
      throw new DBError(e);
    }
    return true;
  }

  private Single<Boolean> drop(Table<?, SQLiteDatabase> table, SQLiteDatabase database) {
    try {
      return checkTableExist(model(table)).onDrop(database);
    } catch (ModelError modelError) {
      return Single.error(modelError);
    }
  }

  public Single<Cursor> query(final QueryBuilder builder) {
    return Single.fromCallable(() -> {
      String sql = builder.build();
      String[] params = builder.buildParameters();
      return getReadableDatabase().rawQuery(sql, params);
    }).subscribeOn(Schedulers.from(Promise.instance().executor()))
        .observeOn(Schedulers.from(Promise.instance().executor()));
  }

  public Context getContext() {
    return context;
  }

  private <T extends S> ReactiveTable<T, SQLiteDatabase> checkTableExist(ReactiveTable<T, SQLiteDatabase> table) {
    return Conditions.checkNotNull(table);
    /*synchronized (this) {
        IndexCreated indexCreated = getIndexCreated(table);
        if (indexCreated.created) {
            return table;
        }
        SQLiteDatabase database = context.openOrCreateDatabase(name(),
                Context.MODE_PRIVATE, null);
        try {
            database.query(table.name(), null, null, null, null, null, null);
        } catch (SQLException e) {
            try {
                table.onCreate(database);
            } catch (ModelError modelError) {
                LogUtil.e(TAG, modelError);
                throw new RuntimeException(modelError);
            }
        }
    }*/
  }



  @Override
  public <T extends S> ReactiveTable.Extras<T> read(Table<T, SQLiteDatabase> table) throws ModelError {
    return checkTableExist(model(table)).read(getReadableDatabase());
  }

  @Override
  public <T extends S> Maybe<SList<T>> readAll(Table<T, SQLiteDatabase> table) {
    try {
      return checkTableExist(model(table)).onReadAll(getReadableDatabase(), true);
    } catch (ModelError modelError) {
      return Maybe.error(modelError);
    }
  }

  @Override
  public <T extends S> Maybe<SList<T>> readAll(Table<T, SQLiteDatabase> table, Column column) {
    try {
      return checkTableExist(model(table)).onReadAll(getReadableDatabase(), column);
    } catch (ModelError modelError) {
      return Maybe.error(modelError);
    }
  }

  @Override
  public <T extends S> Maybe<Boolean> update(T t, Table<T, SQLiteDatabase> table, Column column) {
    try {
      return checkTableExist(model(table)).onUpdate(t, getWritableDatabase(), column);
    } catch (ModelError modelError) {
      return Maybe.error(modelError);
    }
  }

  @Override
  public <T extends S> Maybe<Boolean> update(T t, Table<T, SQLiteDatabase> table) {
    try {
      return checkTableExist(model(table)).onUpdate(t, getWritableDatabase());
    } catch (ModelError modelError) {
      return Maybe.error(modelError);
    }
  }

  @Override
  public <T extends S> Maybe<SList<T>> readAll(Table<T, SQLiteDatabase> table, Column[] columns) {
    try {
      return checkTableExist(model(table)).onReadAll(getReadableDatabase(), columns);
    } catch (ModelError modelError) {
      return Maybe.error(modelError);
    }
  }

  @Override
  public <T extends S> ReactiveTable.Extras<T> read(Table<T, SQLiteDatabase> table, Column... columns) throws ModelError {
    return checkTableExist(model(table)).read(getReadableDatabase(), columns);
  }

  @Override
  public Maybe<Boolean> delete(Table<?, SQLiteDatabase> table, Column column) {
    try {
      return checkTableExist(model(table)).onDelete(getWritableDatabase(), column);
    } catch (ModelError modelError) {
      return Maybe.error(modelError);
    }
  }

  @Override
  public <T extends S> Maybe<Boolean> delete(Table<T, SQLiteDatabase> table, T t) {
    try {
      return checkTableExist(model(table)).onDelete(t, getWritableDatabase());
    } catch (ModelError modelError) {
      return Maybe.error(modelError);
    }
  }

  @Override
  public Maybe<Boolean> delete(Table<?, SQLiteDatabase> table) {
    try {
      return checkTableExist(model(table)).onDelete(getWritableDatabase());
    } catch (ModelError modelError) {
      return Maybe.error(modelError);
    }
  }

  @Override
  public <T> Maybe<Boolean> delete(Table<?, SQLiteDatabase> table, Column<T> column, List<T> list) {
    try {
      return checkTableExist(model(table)).onDelete(getWritableDatabase(), column, list);
    } catch (ModelError modelError) {
      return Maybe.error(modelError);
    }
  }

  @Override
  public <T extends S> Single<Long> save(T t, Table<T, SQLiteDatabase> table) {
    try {
      return checkTableExist(model(table)).onSave(t, getWritableDatabase());
    } catch (ModelError modelError) {
      return Single.error(modelError);
    }
  }

  @Override
  public <T extends S> Single<Boolean> save(SList<T> list, Table<T, SQLiteDatabase> table) {
    try {
      return checkTableExist(model(table)).onSave(list, getWritableDatabase(), true);
    } catch (ModelError modelError) {
      return Single.error(modelError);
    }
  }

  @Override
  public Maybe<Boolean> deleteAll() {
    return Maybe.zip(tables().map(this::delete),
        objects -> List.Companion.fromArray(objects).allMatch(aBoolean -> aBoolean instanceof Boolean &&
            (Boolean) aBoolean)).subscribeOn(Schedulers.from(Promise.instance().executor()))
        .observeOn(Schedulers.from(Promise.instance().executor()));
  }

  @Override
  public Maybe<Integer> getLastId(Table<?, SQLiteDatabase> table) {
    try {
      return checkTableExist(model(table)).onGetLastId(getReadableDatabase());
    } catch (ModelError modelError) {
      return Maybe.error(modelError);
    }
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

  public CompositeDisposable onTerminate() {
    return null;
  }

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

  public <T extends S> ReactiveTable<T, SQLiteDatabase> model(final Table<T, SQLiteDatabase> table) throws ModelError {
    if (!(table instanceof Model)) throw new ModelError(new IllegalStateException("Passed table not a model instance"));
    final Model<SModel> model = (Model<SModel>) table;
    return (ReactiveTable<T, SQLiteDatabase>) new ReactiveModel<SModel>() {
      @Override
      public SModel from(Cursor cursor) {
        return model.from(cursor);
      }

      @Override
      public ContentValues to(SModel t) {
        return model.to((SModel) t);
      }

      @Override
      public String getName() {
        return model.getName();
      }

      @Override
      public List<Column> getColumns() {
        return model.getColumns();
      }
    };
  }
}
