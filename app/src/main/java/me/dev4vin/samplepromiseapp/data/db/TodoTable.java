package me.dev4vin.samplepromiseapp.data.db;

import android.content.ContentValues;
import android.database.Cursor;

import me.dev4vin.samplepromiseapp.models.Todo;
import me.yoctopus.data.db.Column;
import me.yoctopus.data.db.Model;
import me.yoctopus.model.List;

public class TodoTable extends Model<Todo> {

  private static final String tableName = "a";

  public static Column<String> category, name;
  public static Column<Integer> completed;

  static {
    category = new Column<>("a", Column.Type.TEXT.NOT_NULL(), 1);
    name = new Column<>("b", Column.Type.TEXT.NOT_NULL(), 2);
    completed = new Column<>("c", Column.Type.INTEGER.DEFAULT(0), 3);
  }
  @Override
  public List<Column> getColumns() {
    List<Column> columns = new List<>();
    columns.add(category);
    columns.add(name);
    columns.add(completed);
    return columns;
  }

  @Override
  public String getName() {
    return tableName;
  }

  @Override
  public ContentValues get(Todo todo) {
    ContentValues values = new ContentValues();
    values.put(category.getName(), todo.category());
    values.put(name.getName(), todo.name());
    values.put(completed.getName(), todo.completed() ? 1 : 0);
    return values;
  }

  @Override
  public Todo from(Cursor cursor) {
    return new Todo().category(cursor.getString(category.getIndex(cursor)))
        .name(cursor.getString(name.getIndex(cursor)))
        .completed(cursor.getInt(completed.getIndex(cursor)) == 1);
  }
}
