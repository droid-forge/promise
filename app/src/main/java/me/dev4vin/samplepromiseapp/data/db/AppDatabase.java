package me.dev4vin.samplepromiseapp.data.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import me.dev4vin.samplepromiseapp.models.Todo;
import me.dev4vin.Promise;
import me.dev4vin.data.db.Corrupt;
import me.dev4vin.data.db.FastDB;
import me.dev4vin.data.db.Table;
import me.dev4vin.data.db.query.QueryBuilder;
import me.dev4vin.data.log.LogUtil;
import me.dev4vin.model.List;
import me.dev4vin.model.Message;
import me.dev4vin.model.SList;

public class AppDatabase extends FastDB {

  private static final String DB_NAME = "a";
  private static final int DB_VERSION = 1;
  public static final String SENDER_TAG = "App_Database";
  private final String TAG = LogUtil.makeTag(AppDatabase.class);
  private static AppDatabase instance;

  private static TodoTable todoTable;

  static {
    todoTable = new TodoTable();
  }

  private AppDatabase() {
    super(DB_NAME, DB_VERSION, new Corrupt() {
      @Override
      public void onCorrupt() {
        Promise.instance().send(new Message(SENDER_TAG, "Database is corrupted"));
      }
    });
  }

  /**
   * check if the database should be upgraded
   * @param database to be upgraded
   * @param oldVersion current database version
   * @param newVersion new database version
   * @return true if to be upgraded or false
   */
  @Override
  public boolean shouldUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
    return newVersion > oldVersion;
  }

  /**All tables must be registered here
   * @return the list of tables to be created by fast db
   */
  @Override
  public List<Table<?, SQLiteDatabase>> tables() {
    List<Table<?, SQLiteDatabase>> tables = new List<>();
    tables.addAll(List.fromArray(
        todoTable
    ));
    return tables;
  }

  /**
   * @param skip records to skip
   * @param limit records to take
   * @return a list of todos
   */
  public List<Todo> todos(int skip, int limit) {
    List<Todo> todos = new List<>();
    Cursor cursor = query(new QueryBuilder().from(todoTable).skip(skip).take(limit));
    while (cursor.moveToNext()) todos.add(todoTable.from(cursor));
    return todos;
  }

  /**
   * @param category todo category
   * @return a list of todos matching this category
   */
  public List<Todo> todos(String category) {
    return readAll(todoTable, TodoTable.category.with(category));
  }

  /**
   * @param todos to be saved
   * @return true if all todos are saved
   */
  public boolean saveTodos(List<Todo> todos) {
    return save(new SList(todos), todoTable);
  }

  public long addTodo(Todo todo) {
    return save(todo, todoTable);
  }

  public boolean updateTodo(Todo todo) {
    return update(todo, todoTable);
  }

  public boolean deleteTodo(Todo todo) {
    return delete(todoTable, todo);
  }

  public boolean clearTodos() {
    return delete(todoTable);
  }

  public static AppDatabase instance() {
    if (instance == null) instance = new AppDatabase();
    return instance;
  }
}
