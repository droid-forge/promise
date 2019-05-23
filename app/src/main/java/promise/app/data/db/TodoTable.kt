package promise.app.data.db

import android.content.ContentValues
import android.database.Cursor

import promise.app.models.Todo
import promise.data.db.Column
import promise.data.db.Model
import promise.model.List

class TodoTable : Model<Todo>() {
  override fun getColumns(): List<Column<*>> = object : List<Column<*>>() {
    init {
      add(category)
      add(namee)
      add(completed)
    }
  }

  override fun getName(): String = tableName

  override fun get(todo: Todo): ContentValues = ContentValues().apply {
    put(category.name, todo.category())
    put(namee.name, todo.name())
    put(completed.name, if (todo.completed()) 1 else 0)
  }

  override fun from(cursor: Cursor): Todo =
      Todo().category(cursor.getString(category.getIndex(cursor)))
          .name(cursor.getString(namee.getIndex(cursor)))
          .completed(cursor.getInt(completed.getIndex(cursor)) == 1)

  companion object {
    private const val tableName = "a"

    var category: Column<String> = Column("a", Column.Type.TEXT.NOT_NULL(), 1)
    var namee: Column<String> = Column("b", Column.Type.TEXT.NOT_NULL(), 2)
    var completed: Column<Int> = Column("c", Column.Type.INTEGER.DEFAULT(0), 3)

  }
}
