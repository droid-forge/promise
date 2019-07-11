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

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import promise.data.log.LogUtil;
import promise.model.List;
import promise.model.SList;
import promise.model.SModel;
import promise.promisedb.query.QueryBuilder;
import promise.promisedb.query.criteria.Criteria;
import promise.promisedb.query.projection.Projection;
import promise.util.Conditions;
import promise.util.DoubleConverter;

public abstract class Model<T extends SModel>
    implements Table<T, SQLiteDatabase>, DoubleConverter<T, Cursor, ContentValues> {
  private static final String CREATE_PREFIX = "CREATE TABLE IF NOT EXISTS ";
  private static final String DROP_PREFIX = "TRUNCATE TABLE IF EXISTS ";
  private static final String SELECT_PREFIX = "SELECT * FROM ";
  public static Column<Integer> id, createdAt, updatedAt;

  static {
    id = new Column<>("id", Column.Type.INTEGER.PRIMARY_KEY_AUTOINCREMENT());
  }

  private String ALTER_COMMAND = "ALTER TABLE";
  private String name = "`" + getName() + "`";
  private String TAG = LogUtil.makeTag(Model.class).concat(name);
  private SList<T> backup;

  public abstract List<Column> getColumns();

  public int getNumberOfColumns() {
    return getColumns().size() + 3;
  }

  @Override
  public boolean onCreate(SQLiteDatabase database) throws ModelError {
    String sql = CREATE_PREFIX;
    sql = sql.concat(name + "(");
    List<Column> columns = getColumns();
    columns = Conditions.checkNotNull(columns);
    Collections.sort(columns, Column.ascending);
    List<Column> columns1 = new List<>();
    columns1.add(id);
    columns1.addAll(columns);
    if (createdAt == null) createdAt = new Column<>("CREATED_AT", Column.Type.INTEGER.NULLABLE());
    if (updatedAt == null) updatedAt = new Column<>("UPDATED_AT", Column.Type.INTEGER.NULLABLE());
    columns1.add(createdAt);
    columns1.add(updatedAt);
    for (int i = 0; i < columns1.size(); i++) {
      Column column = columns1.get(i);
      if (i == columns1.size() - 1) sql = sql.concat(column.toString());
      else sql = sql.concat(column.toString() + ", ");
    }
    sql = sql.concat(");");
    try {
      LogUtil.d(TAG, sql);
      database.execSQL(sql);
    } catch (SQLException e) {
      throw new ModelError(e);
    }
    return true;
  }

  @Override
  public boolean onUpgrade(SQLiteDatabase database, int v1, int v2) throws ModelError {
    if (createdAt == null) createdAt = new Column<>("CREATED_AT", Column.Type.INTEGER.NULLABLE());
    if (updatedAt == null) updatedAt = new Column<>("UPDATED_AT", Column.Type.INTEGER.NULLABLE());
    QueryBuilder builder = new QueryBuilder().from(this);
    Cursor c = database.rawQuery(builder.build(), builder.buildParameters());
    Set<String> set = new HashSet<>(List.fromArray(c.getColumnNames()));
    if (!set.contains(createdAt.getName())) addColumns(database, createdAt);
    if (!set.contains(updatedAt.getName())) addColumns(database, updatedAt);
    return false;
  }

  public boolean addColumns(SQLiteDatabase database, Column... columns) throws ModelError {
    for (Column column : columns) {
      String alterSql = ALTER_COMMAND + " `" + getName() + "` " + "ADD " + column.toString() + ";";
      try {
        LogUtil.d(TAG, alterSql);
        database.execSQL(alterSql);
      } catch (SQLException e) {
        throw new ModelError(e);
      }
    }
    return true;
  }

  public boolean dropColumns(SQLiteDatabase database, Column... columns) throws ModelError {
    for (Column column : columns) {
      String alterSql =
          ALTER_COMMAND + " `" + getName() + "` " + "DROP COLUMN " + column.getName() + ";";
      try {
        database.execSQL(alterSql);
      } catch (SQLException e) {
        throw new ModelError(e);
      }
    }
    return true;
  }

  @Override
  public Extras<T> read(final SQLiteDatabase database) {
    return new QueryExtras<T>(database) {
      @Override
      public ContentValues to(T t) {
        return Model.this.to(t);
      }

      @Override
      public T from(Cursor cursor) {
        return Model.this.from(cursor);
      }
    };
  }

  @Override
  public Extras<T> read(SQLiteDatabase database, Column... columns) {
    return new QueryExtras2<T>(database, columns) {
      @Override
      public ContentValues to(T t) {
        return Model.this.to(t);
      }

      @Override
      public T from(Cursor cursor) {
        return Model.this.from(cursor);
      }
    };
  }

  @Override
  public final SList<T> onReadAll(SQLiteDatabase database, boolean close) {
    Cursor cursor;
    try {
      String sql = SELECT_PREFIX + name + ";";
      LogUtil.d(TAG, sql);
      cursor = database.rawQuery(sql, null);
      SList<T> ts = new SList<>();
      while (cursor.moveToNext() && !cursor.isClosed()) ts.add(getWithId(cursor));
      cursor.close();
      /*if (close) database.close();*/
      return ts;
    } catch (SQLiteException e) {
      return new SList<>();
    }
  }

  @Override
  public SList<T> onReadAll(SQLiteDatabase database, Column column) {
    if (column == null) return onReadAll(database, true);
    String where = column.getName() + column.getOperand();
    if (column.value() instanceof String) where = where + "\"" + column.value() + "\"";
    else where = where + column.value();
    Cursor cursor = database.query(name, null, where, null, null, null, null);
    SList<T> ts = new SList<>();
    while (cursor.moveToNext() && !cursor.isClosed()) ts.add(getWithId(cursor));
    cursor.close();
    /*database.close();*/
    return ts;
  }

  @Override
  public SList<T> onReadAll(SQLiteDatabase database, Column[] columns) {
    if (columns == null) return onReadAll(database, true);
    String[] args = new String[columns.length];
    String selection = "";
    for (int i = 0; i < args.length; i++) {
      Column column = columns[i];
      String where = column.getName() + column.getOperand();
      selection = selection.concat(where);
      if (column.value() instanceof String) args[i] = "\"" + column.value() + "\"";
      else args[i] = column.value().toString();
      selection = selection.concat(args[i]);
      if (i < args.length - 1) selection = selection.concat(" AND ");
    }
    Cursor cursor = database.query(name, null, selection, null, null, null, null);
    SList<T> ts = new SList<>();
    while (cursor.moveToNext() && !cursor.isClosed()) ts.add(getWithId(cursor));
    cursor.close();
    /*database.close();*/
    return ts;
  }

  @Override
  public final boolean onUpdate(T t, SQLiteDatabase database, Column column) {
    String where = null;
    if (column != null) where = column.getName() + column.getOperand() + column.value();
    ContentValues values = to(t);
    if (updatedAt == null) updatedAt = new Column<>("UPDATED_AT", Column.Type.INTEGER.NULLABLE());
    values.put(updatedAt.getName(), System.currentTimeMillis());
    return database.update(name, values, where, null) >= 0;
  }

  @Override
  public boolean onUpdate(T t, SQLiteDatabase database) {
    return id != null && onUpdate(t, database, id.with(t.id()));
  }

  @Override
  public final <C> boolean onDelete(SQLiteDatabase database, Column<C> column, List<C> list) {
    boolean deleted = true;
    String where;
    for (int i = 0, listSize = list.size(); i < listSize; i++) {
      C c = list.get(i);
      where = column.getName() + column.getOperand() + c;
      deleted = database.delete(name, where, null) >= 0;
    }
    return deleted;
  }

  @Override
  public final boolean onDelete(SQLiteDatabase database, Column column) {
    if (column == null) return false;
    String where = column.getName() + column.getOperand() + column.value();
    return database.delete(name, where, null) >= 0;
  }

  @Override
  public boolean onDelete(T t, SQLiteDatabase database) {
    return onDelete(database, id.with(t.id()));
  }

  @Override
  public final boolean onDelete(SQLiteDatabase database) {
    return !TextUtils.isEmpty(name) && database.delete(name, null, null) >= 0;
  }

  @Override
  public final long onSave(T t, SQLiteDatabase database) {
    if (t.id() != 0 && onUpdate(t, database)) return t.id();
    ContentValues values = to(t);
    if (createdAt == null) createdAt = new Column<>("CREATED_AT", Column.Type.INTEGER.NULLABLE());
    if (updatedAt == null) updatedAt = new Column<>("UPDATED_AT", Column.Type.INTEGER.NULLABLE());
    values.put(createdAt.getName(), System.currentTimeMillis());
    values.put(updatedAt.getName(), System.currentTimeMillis());
    return database.insert(name, null, values);
  }

  @Override
  public final boolean onSave(SList<T> list, SQLiteDatabase database, boolean close) {
    boolean saved = true;
    int i = 0, listSize = list.size();
    while (i < listSize) {
      T t = list.get(i);
      saved = saved && onSave(t, database) > 0;
      i++;
    }
    /*if (close) database.close();*/
    return saved;
  }

  @Override
  public final boolean onDrop(SQLiteDatabase database) throws ModelError {
    String sql = DROP_PREFIX + name + ";";
    try {
      database.execSQL(sql);
    } catch (SQLException e) {
      throw new ModelError(e);
    }
    return true;
  }

  @Override
  public final int onGetLastId(SQLiteDatabase database) {
    if (id == null) return 0;
    QueryBuilder builder = new QueryBuilder().from(this).select(Projection.count(id).as("num"));
    Cursor cursor = database.rawQuery(builder.build(), builder.buildParameters());
    return cursor.getInt(Model.id.getIndex());
  }

  @Override
  public void backup(SQLiteDatabase database) {
    backup = onReadAll(database, false);
  }

  @Override
  public void restore(SQLiteDatabase database) {
    if (backup != null && !backup.isEmpty()) onSave(backup, database, false);
  }

  T getWithId(Cursor cursor) {
    T t = from(cursor);
    t.id(cursor.getInt(id.getIndex()));
    if (createdAt == null) createdAt = new Column<>("CREATED_AT", Column.Type.INTEGER.NULLABLE());
    if (updatedAt == null) updatedAt = new Column<>("UPDATED_AT", Column.Type.INTEGER.NULLABLE());
    t.createdAt(cursor.getInt(createdAt.getIndex(cursor)));
    t.updatedAt(cursor.getInt(updatedAt.getIndex(cursor)));
    return t;
  }

  private abstract class QueryExtras<Q extends SModel>
      implements Extras<Q>, DoubleConverter<Q, Cursor, ContentValues> {

    private SQLiteDatabase database;

    QueryExtras(SQLiteDatabase database) {
      this.database = database;
    }

    public SQLiteDatabase database() {
      return database;
    }

    Q getWithId(Cursor cursor) {
      Q t = from(cursor);
      t.id(cursor.getInt(id.getIndex()));
      if (createdAt == null) createdAt = new Column<>("CREATED_AT", Column.Type.INTEGER.NULLABLE());
      if (updatedAt == null) updatedAt = new Column<>("UPDATED_AT", Column.Type.INTEGER.NULLABLE());
      t.createdAt(cursor.getInt(createdAt.getIndex(cursor)));
      t.updatedAt(cursor.getInt(updatedAt.getIndex(cursor)));
      return t;
    }

    @Nullable
    @Override
    public Q first() {
      Cursor cursor;
      try {
        QueryBuilder builder = new QueryBuilder().from(Model.this).take(1);
        cursor = database.rawQuery(builder.build(), builder.buildParameters());
        return getWithId(cursor);
      } catch (SQLiteException e) {
        LogUtil.e(TAG, e);
        return null;
      }
    }

    @Nullable
    @Override
    public Q last() {
      Cursor cursor;
      try {
        QueryBuilder builder = new QueryBuilder().from(Model.this).orderByDescending(id).take(1);
        cursor = database.rawQuery(builder.build(), builder.buildParameters());
        return getWithId(cursor);
      } catch (SQLiteException e) {
        LogUtil.e(TAG, e);
        return null;
      }
    }

    @Override
    public SList<Q> all() {
      Cursor cursor;
      try {
        QueryBuilder builder = new QueryBuilder().from(Model.this).takeAll();
        cursor = database.rawQuery(builder.build(), builder.buildParameters());
      } catch (SQLiteException e) {
        return new SList<>();
      }
      SList<Q> ts = new SList<>();
      while (cursor.moveToNext() && !cursor.isClosed()) ts.add(getWithId(cursor));
      return ts;
    }

    @Override
    public SList<Q> limit(int limit) {
      Cursor cursor;
      try {
        QueryBuilder builder = new QueryBuilder().from(Model.this).take(1);
        cursor = database.rawQuery(builder.build(), builder.buildParameters());
        SList<Q> ts = new SList<>();
        while (cursor.moveToNext() && !cursor.isClosed()) ts.add(getWithId(cursor));
        return ts;
      } catch (SQLiteException e) {
        LogUtil.e(TAG, e);
        return new SList<>();
      }
    }

    @Override
    public SList<Q> between(Column<Integer> column, Integer a, Integer b) {
      Cursor cursor;
      try {
        QueryBuilder builder = new QueryBuilder().from(Model.this).whereAnd(Criteria.between(column, a, b));
        cursor = database.rawQuery(builder.build(), builder.buildParameters());
        SList<Q> ts = new SList<>();
        while (cursor.moveToNext() && !cursor.isClosed()) ts.add(getWithId(cursor));
        return ts;
      } catch (SQLiteException e) {
        LogUtil.e(TAG, e);
        return new SList<>();
      }
    }

    @Override
    public SList<Q> where(Column[] column) {
      return null;
    }

    @Override
    public SList<Q> notIn(Column<Integer> column, Integer a, Integer b) {
      Cursor cursor;
      try {
        String sql =
            SELECT_PREFIX
                + name
                + " WHERE "
                + column.getName()
                + " NOT BETWEEN "
                + a
                + " AND "
                + b
                + ";";
        cursor = database.rawQuery(sql, null);
        SList<Q> ts = new SList<>();
        while (cursor.moveToNext() && !cursor.isClosed()) {
          Q t = getWithId(cursor);
          ts.add(t);
        }
        cursor.close();
        /*database.close();*/
        return ts;
      } catch (SQLiteException e) {
        LogUtil.e(TAG, e);
        return new SList<>();
      }
    }

    @Override
    public SList<Q> like(Column[] column) {
      return null;
    }

    @Override
    public SList<Q> orderBy(Column column) {
      Cursor cursor;
      try {
        String sql =
            SELECT_PREFIX + name + " ORDER BY " + column.getName() + " " + column.order() + ";";
        cursor = database.rawQuery(sql, null);
        SList<Q> ts = new SList<>();
        while (cursor.moveToNext() && !cursor.isClosed()) {
          Q t = getWithId(cursor);
          ts.add(t);
        }
        cursor.close();
        /*database.close();*/
        return ts;
      } catch (SQLiteException e) {
        LogUtil.e(TAG, e);
        return new SList<>();
      }
    }

    @Override
    public SList<Q> groupBy(Column column) {
      Cursor cursor;
      try {
        String sql =
            SELECT_PREFIX + name + " GROUP BY " + column.getName() + " " + column.order() + ";";
        cursor = database.rawQuery(sql, null);
        SList<Q> ts = new SList<>();
        while (cursor.moveToNext() && !cursor.isClosed()) {
          Q t = getWithId(cursor);
          ts.add(t);
        }
        cursor.close();
        /*database.close();*/
        return ts;
      } catch (SQLiteException e) {
        LogUtil.e(TAG, e);
        return new SList<>();
      }
    }

    @Override
    public SList<Q> groupAndOrderBy(Column column, Column column1) {
      Cursor cursor;
      try {
        String sql =
            SELECT_PREFIX
                + name
                + " GROUP BY "
                + column.getName()
                + " "
                + column.order()
                + " ORDER BY "
                + column.getName()
                + " "
                + column.order()
                + ";";
        cursor = database.rawQuery(sql, null);
        SList<Q> ts = new SList<>();
        while (cursor.moveToNext() && !cursor.isClosed()) {
          Q t = getWithId(cursor);
          ts.add(t);
        }
        cursor.close();
        /*database.close();*/
        return ts;
      } catch (SQLiteException e) {
        LogUtil.e(TAG, e);
        return new SList<>();
      }
    }
  }

  private abstract class QueryExtras2<U extends SModel> extends QueryExtras<U> {
    private Column[] columns;

    QueryExtras2(SQLiteDatabase database, Column[] columns) {
      super(database);
      this.columns = columns;
    }

    @Nullable
    @Override
    public U first() {
      SList<U> list = all();
      if (!list.isEmpty()) return list.get(0);
      return null;
    }

    @Nullable
    @Override
    public U last() {
      SList<U> list = all();
      if (!list.isEmpty()) return list.get(list.size() - 1);
      return null;
    }

    @Override
    public SList<U> all() {
      if (columns == null || columns.length == 0) return super.all();
      String[] args = new String[columns.length];
      String selection = "";
      for (int i = 0; i < args.length; i++) {
        Column column = columns[i];
        String where = column.getName() + column.getOperand();
        selection = selection.concat(where);
        if (column.value() instanceof String) args[i] = "\"" + column.value() + "\"";
        else args[i] = column.value().toString();
        selection = selection.concat(args[i]);
        if (i < args.length - 1) selection = selection.concat(" AND ");
      }
      Cursor cursor = database().query(name, null, selection, null, null, null, null);
      SList<U> ts = new SList<>();
      while (cursor.moveToNext() && !cursor.isClosed()) {
        U t = getWithId(cursor);
        ts.add(t);
      }
      cursor.close();
      /*database().close();*/
      return ts;
    }

    @Override
    public SList<U> limit(int limit) {
      return null;
    }

    @Override
    public SList<U> between(Column<Integer> column, Integer a, Integer b) {
      return super.between(column, a, b);
    }

    @Override
    public SList<U> where(Column[] column) {
      return null;
    }

    @Override
    public SList<U> notIn(Column<Integer> column, Integer a, Integer b) {
      return super.notIn(column, a, b);
    }

    @Override
    public SList<U> like(Column[] column) {
      return null;
    }

    @Override
    public SList<U> orderBy(Column column) {
      return null;
    }

    @Override
    public SList<U> groupBy(Column column) {
      return null;
    }

    @Override
    public SList<U> groupAndOrderBy(Column column, Column column1) {
      return null;
    }
  }
}
