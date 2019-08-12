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

package promise.data.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.concurrent.Callable;

import androidx.annotation.Nullable;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import promise.Promise;
import promise.model.List;
import promise.model.S;
import promise.model.SList;
import promise.model.SModel;

public abstract class ReactiveModel<T extends SModel>
    implements ReactiveTable<T, SQLiteDatabase>, Converter<T, Cursor, ContentValues> {
  private Model<T> table = new Model<T>() {
    @Override
    public List<Column> getColumns() {
      return ReactiveModel.this.getColumns();
    }

    @Override
    public String getName() {
      return ReactiveModel.this.getName();
    }

    @Override
    public ContentValues get(T t) {
      return ReactiveModel.this.get(t);
    }

    @Override
    public T from(Cursor cursor) {
      return ReactiveModel.this.from(cursor);
    }
  };

  public abstract List<Column> getColumns();

  public Model<T> table() {
    return table;
  }

  @Override
  public boolean onCreate(SQLiteDatabase database) throws ModelError {
    return table.onCreate(database);
  }

  @Override
  public boolean onUpgrade(SQLiteDatabase database, int v1, int v2) throws ModelError {
    return table.onUpgrade(database, v1, v2);
  }

  public boolean addColumns(SQLiteDatabase database, Column... columns) throws ModelError {
    return table.addColumns(database, columns);
  }

  public boolean dropColumns(SQLiteDatabase database, Column... columns) throws ModelError {
    return table.dropColumns(database, columns);
  }

  @Override
  public ReactiveTable.Extras<T> read(final SQLiteDatabase database) {
    return new QueryExtras<T>(database) {
      @Override
      public ContentValues get(T t) {
        return ReactiveModel.this.get(t);
      }

      @Override
      public T from(Cursor cursor) {
        return ReactiveModel.this.from(cursor);
      }
    };
  }

  @Override
  public ReactiveTable.Extras<T> read(SQLiteDatabase database, Column... columns) {
    return new QueryExtras2<T>(database, columns) {
      @Override
      public ContentValues get(T t) {
        return ReactiveModel.this.get(t);
      }

      @Override
      public T from(Cursor cursor) {
        return ReactiveModel.this.from(cursor);
      }
    };
  }

  @Override
  public final Maybe<SList<T>> onReadAll(final SQLiteDatabase database, final boolean close) {
    return Maybe.fromCallable(() -> table.onReadAll(database, close))
        .subscribeOn(Schedulers.from(Promise.instance().executor()))
        .observeOn(Schedulers.from(Promise.instance().executor()));
  }

  @Override
  public Maybe<SList<T>> onReadAll(final SQLiteDatabase database, final Column column) {
    return Maybe.fromCallable(() -> table.onReadAll(database, column))
        .subscribeOn(Schedulers.from(Promise.instance().executor()))
        .observeOn(Schedulers.from(Promise.instance().executor()));
  }

  @Override
  public Maybe<SList<T>> onReadAll(final SQLiteDatabase database, final Column[] columns) {
    return Maybe.fromCallable(() -> table.onReadAll(database, columns))
        .subscribeOn(Schedulers.from(Promise.instance().executor()))
        .observeOn(Schedulers.from(Promise.instance().executor()));
  }

  @Override
  public final Maybe<Boolean> onUpdate(final T t, final SQLiteDatabase database, final Column column) {
    return Maybe.fromCallable(() -> table.onUpdate(t, database, column))
        .subscribeOn(Schedulers.from(Promise.instance().executor()))
        .observeOn(Schedulers.from(Promise.instance().executor()));
  }

  @Override
  public Maybe<Boolean> onUpdate(final T t, final SQLiteDatabase database) {
    return Maybe.fromCallable(() -> table.onUpdate(t, database))
        .subscribeOn(Schedulers.from(Promise.instance().executor()));
  }

  @Override
  public final <C> Maybe<Boolean> onDelete(final SQLiteDatabase database, final Column<C> column, final List<C> list) {
    return Maybe.fromCallable(() -> table.onDelete(database, column, list))
        .subscribeOn(Schedulers.from(Promise.instance().executor()))
        .observeOn(Schedulers.from(Promise.instance().executor()));
  }

  @Override
  public final Maybe<Boolean> onDelete(final SQLiteDatabase database, final Column column) {
    return Maybe.fromCallable(() -> table.onDelete(database, column))
        .subscribeOn(Schedulers.from(Promise.instance().executor()))
        .observeOn(Schedulers.from(Promise.instance().executor()));
  }

  @Override
  public Maybe<Boolean> onDelete(final T t, final SQLiteDatabase database) {
    return Maybe.fromCallable(() -> table.onDelete(t, database))
        .subscribeOn(Schedulers.from(Promise.instance().executor()))
        .observeOn(Schedulers.from(Promise.instance().executor()));
  }

  @Override
  public final Maybe<Boolean> onDelete(final SQLiteDatabase database) {
    return Maybe.fromCallable(() -> table.onDelete(database))
        .subscribeOn(Schedulers.from(Promise.instance().executor()))
        .observeOn(Schedulers.from(Promise.instance().executor()));
  }

  @Override
  public final Single<Long> onSave(final T t, final SQLiteDatabase database) {
    return Single.fromCallable(() -> table.onSave(t, database))
        .subscribeOn(Schedulers.from(Promise.instance().executor()))
        .observeOn(Schedulers.from(Promise.instance().executor()));
  }

  @Override
  public final Single<Boolean> onSave(final SList<T> list, final SQLiteDatabase database, final boolean close) {
    return Single.fromCallable(() -> table.onSave(list, database, close))
        .subscribeOn(Schedulers.from(Promise.instance().executor()))
        .observeOn(Schedulers.from(Promise.instance().executor()));
  }

  @Override
  public final Single<Boolean> onDrop(final SQLiteDatabase database) {
    return Single.fromCallable(() -> table.onDrop(database))
        .subscribeOn(Schedulers.from(Promise.instance().executor()))
        .observeOn(Schedulers.from(Promise.instance().executor()));
  }

  @Override
  public final Maybe<Integer> onGetLastId(final SQLiteDatabase database) {
    return Maybe.fromCallable(() -> table.onGetLastId(database))
        .subscribeOn(Schedulers.from(Promise.instance().executor()))
        .observeOn(Schedulers.from(Promise.instance().executor()));
  }

  @Override
  public Completable backup(final SQLiteDatabase database) {
    return Completable.fromMaybe((MaybeSource<Boolean>) observer -> {
      table.backup(database);
      observer.onComplete();
    }).subscribeOn(Schedulers.from(Promise.instance().executor()))
        .observeOn(Schedulers.from(Promise.instance().executor()));
  }

  @Override
  public Completable restore(final SQLiteDatabase database) {
    return Completable.fromMaybe((MaybeSource<Boolean>) observer -> {
      table.restore(database);
      observer.onComplete();
    }).subscribeOn(Schedulers.from(Promise.instance().executor()))
        .observeOn(Schedulers.from(Promise.instance().executor()));
  }

  private T getWithId(Cursor cursor) {
    return table.getWithId(cursor);
  }

  private abstract class QueryExtras<Q extends S> implements ReactiveModel.Extras<Q>, Converter<Q, Cursor, ContentValues> {

    private SQLiteDatabase database;

    QueryExtras(SQLiteDatabase database) {
      this.database = database;
    }

    public SQLiteDatabase database() {
      return database;
    }

    @Nullable
    @Override
    public Maybe<Q> first() {
      return Maybe.fromCallable(() -> (Q) table.read(database).first())
          .subscribeOn(Schedulers.from(Promise.instance().executor()))
          .observeOn(Schedulers.from(Promise.instance().executor()));
    }

    @Nullable
    @Override
    public Maybe<Q> last() {
      return Maybe.fromCallable(() -> (Q) table.read(database).last())
          .subscribeOn(Schedulers.from(Promise.instance().executor()))
          .observeOn(Schedulers.from(Promise.instance().executor()));
    }

    @Override
    public Maybe<SList<Q>> all() {
      return Maybe.fromCallable(() -> (SList<Q>) table.read(database).all())
          .subscribeOn(Schedulers.from(Promise.instance().executor()))
          .observeOn(Schedulers.from(Promise.instance().executor()));
    }

    @Override
    public Maybe<SList<Q>> limit(final int limit) {
      return Maybe.fromCallable(() -> (SList<Q>) table.read(database).limit(limit))
          .subscribeOn(Schedulers.from(Promise.instance().executor()))
          .observeOn(Schedulers.from(Promise.instance().executor()));
    }

    @Override
    public Maybe<SList<Q>> between(final Column<Integer> column, final Integer a, final Integer b) {
      return Maybe.fromCallable(() -> (SList<Q>) table.read(database).between(column, a, b))
          .subscribeOn(Schedulers.from(Promise.instance().executor()))
          .observeOn(Schedulers.from(Promise.instance().executor()));
    }

    @Override
    public Maybe<SList<Q>> where(final Column[] column) {
      return Maybe.fromCallable(() -> (SList<Q>) table.read(database).where(column))
          .subscribeOn(Schedulers.from(Promise.instance().executor()))
          .observeOn(Schedulers.from(Promise.instance().executor()));
    }

    @Override
    public Maybe<SList<Q>> notIn(final Column<Integer> column, final Integer a, final Integer b) {
      return Maybe.fromCallable(new Callable<SList<Q>>() {
        @Override
        public SList<Q> call() throws Exception {
          return (SList<Q>) table.read(database).notIn(column, a, b);
        }
      }).subscribeOn(Schedulers.from(Promise.instance().executor()))
          .observeOn(Schedulers.from(Promise.instance().executor()));
    }

    @Override
    public Maybe<SList<Q>> like(final Column[] column) {
      return Maybe.fromCallable(new Callable<SList<Q>>() {
        @Override
        public SList<Q> call() throws Exception {
          return (SList<Q>) table.read(database).like(column);
        }
      }).subscribeOn(Schedulers.from(Promise.instance().executor()))
          .observeOn(Schedulers.from(Promise.instance().executor()));
    }

    @Override
    public Maybe<SList<Q>> orderBy(final Column column) {
      return Maybe.fromCallable(new Callable<SList<Q>>() {
        @Override
        public SList<Q> call() throws Exception {
          return (SList<Q>) table.read(database).orderBy(column);
        }
      }).subscribeOn(Schedulers.from(Promise.instance().executor()))
          .observeOn(Schedulers.from(Promise.instance().executor()));
    }

    @Override
    public Maybe<SList<Q>> groupBy(final Column column) {
      return Maybe.fromCallable(new Callable<SList<Q>>() {
        @Override
        public SList<Q> call() throws Exception {
          return (SList<Q>) table.read(database).groupBy(column);
        }
      }).subscribeOn(Schedulers.from(Promise.instance().executor()))
          .observeOn(Schedulers.from(Promise.instance().executor()));
    }

    @Override
    public Maybe<SList<Q>> groupAndOrderBy(final Column column, final Column column1) {
      return Maybe.fromCallable(new Callable<SList<Q>>() {
        @Override
        public SList<Q> call() throws Exception {
          return (SList<Q>) table.read(database).groupAndOrderBy(column, column1);
        }
      }).subscribeOn(Schedulers.from(Promise.instance().executor()))
          .observeOn(Schedulers.from(Promise.instance().executor()));
    }
  }

  private abstract class QueryExtras2<U extends S> extends QueryExtras<U> {
    private Column[] columns;

    QueryExtras2(SQLiteDatabase database, Column[] columns) {
      super(database);
      this.columns = columns;
    }

    @Override
    public Maybe<U> first() {
      return Maybe.fromCallable(new Callable<U>() {
        @Override
        public U call() throws Exception {
          return (U) table.read(database(), columns).first();
        }
      }).subscribeOn(Schedulers.from(Promise.instance().executor()));
    }

    @Override
    public Maybe<U> last() {
      return Maybe.fromCallable(new Callable<U>() {
        @Override
        public U call() throws Exception {
          return (U) table.read(database(), columns).last();
        }
      }).subscribeOn(Schedulers.from(Promise.instance().executor()))
          .observeOn(Schedulers.from(Promise.instance().executor()));

    }

    @Override
    public Maybe<SList<U>> all() {
      return Maybe.fromCallable(new Callable<SList<U>>() {
        @Override
        public SList<U> call() throws Exception {
          return (SList<U>) table.read(database(), columns).all();
        }
      }).subscribeOn(Schedulers.from(Promise.instance().executor()))
          .observeOn(Schedulers.from(Promise.instance().executor()));
    }

    @Override
    public Maybe<SList<U>> limit(final int limit) {
      return Maybe.fromCallable(new Callable<SList<U>>() {
        @Override
        public SList<U> call() throws Exception {
          return (SList<U>) table.read(database(), columns).limit(limit);
        }
      }).subscribeOn(Schedulers.from(Promise.instance().executor()))
          .observeOn(Schedulers.from(Promise.instance().executor()));
    }

    @Override
    public Maybe<SList<U>> between(final Column<Integer> column, final Integer a, final Integer b) {
      return Maybe.fromCallable(new Callable<SList<U>>() {
        @Override
        public SList<U> call() throws Exception {
          return (SList<U>) table.read(database(), columns).between(column, a, b);
        }
      }).subscribeOn(Schedulers.from(Promise.instance().executor()))
          .observeOn(Schedulers.from(Promise.instance().executor()));
    }

    @Override
    public Maybe<SList<U>> where(final Column[] column) {
      return Maybe.fromCallable(new Callable<SList<U>>() {
        @Override
        public SList<U> call() throws Exception {
          return (SList<U>) table.read(database(), columns).where(column);
        }
      }).subscribeOn(Schedulers.from(Promise.instance().executor()))
          .observeOn(Schedulers.from(Promise.instance().executor()));
    }

    @Override
    public Maybe<SList<U>> notIn(final Column<Integer> column, final Integer a, final Integer b) {
      return Maybe.fromCallable(new Callable<SList<U>>() {
        @Override
        public SList<U> call() throws Exception {
          return (SList<U>) table.read(database(), columns).notIn(column, a, b);
        }
      }).subscribeOn(Schedulers.from(Promise.instance().executor()))
          .observeOn(Schedulers.from(Promise.instance().executor()));
    }

    @Override
    public Maybe<SList<U>> like(final Column[] column) {
      return Maybe.fromCallable(new Callable<SList<U>>() {
        @Override
        public SList<U> call() throws Exception {
          return (SList<U>) table.read(database(), columns).like(column);
        }
      }).subscribeOn(Schedulers.from(Promise.instance().executor()))
          .observeOn(Schedulers.from(Promise.instance().executor()));
    }

    @Override
    public Maybe<SList<U>> orderBy(final Column column) {
      return Maybe.fromCallable(new Callable<SList<U>>() {
        @Override
        public SList<U> call() throws Exception {
          return (SList<U>) table.read(database(), columns).orderBy(column);
        }
      }).subscribeOn(Schedulers.from(Promise.instance().executor()))
          .observeOn(Schedulers.from(Promise.instance().executor()));
    }

    @Override
    public Maybe<SList<U>> groupBy(final Column column) {
      return Maybe.fromCallable(new Callable<SList<U>>() {
        @Override
        public SList<U> call() throws Exception {
          return (SList<U>) table.read(database(), columns).groupBy(column);
        }
      }).subscribeOn(Schedulers.from(Promise.instance().executor()))
          .observeOn(Schedulers.from(Promise.instance().executor()));
    }

    @Override
    public Maybe<SList<U>> groupAndOrderBy(final Column column, final Column column1) {
      return Maybe.fromCallable(new Callable<SList<U>>() {
        @Override
        public SList<U> call() throws Exception {
          return (SList<U>) table.read(database(), columns).groupAndOrderBy(column, column1);
        }
      }).subscribeOn(Schedulers.from(Promise.instance().executor()))
          .observeOn(Schedulers.from(Promise.instance().executor()));
    }
  }
}


